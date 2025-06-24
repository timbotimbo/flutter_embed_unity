package com.learntoflutter.flutter_embed_unity_android

import com.learntoflutter.flutter_embed_unity_android.constants.FlutterEmbedConstants.Companion.logTag
import com.learntoflutter.flutter_embed_unity_android.constants.FlutterEmbedConstants.Companion.uniqueIdentifier
import com.learntoflutter.flutter_embed_unity_android.messaging.SendToFlutter
import com.learntoflutter.flutter_embed_unity_android.messaging.SendToUnity
import com.learntoflutter.flutter_embed_unity_android.platformView.UnityViewFactory
import com.learntoflutter.flutter_embed_unity_android.unity.ResumeUnityOnActivityResume
import com.learntoflutter.flutter_embed_unity_android.unity.UnityPlayerSingleton
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodChannel
import io.flutter.Log
import io.flutter.embedding.engine.plugins.lifecycle.HiddenLifecycleReference

/**
 * This is the entry point for the plugin. The Flutter engine will call onAttachedToEngine first.
 *
 * The plugin implements ActivityAware so that it can respond to Android activity-related events.
 * See https://docs.flutter.dev/release/breaking-changes/plugin-api-migration#uiactivity-plugin
 * and https://api.flutter.dev/javadoc/io/flutter/embedding/engine/plugins/activity/ActivityAware.html
 * */
class FlutterEmbedUnityAndroidPlugin : FlutterPlugin, ActivityAware {
    // The MethodChannel that will handle the communication between Flutter and this plugin.
    // Should only be assigned for the main Flutter engine that renders UI, not for background engines from other plugins.
    private var channel: MethodChannel? = null

    // The messenger for this Fluter Engine. Might be used to setup the MethodChannel above.
    private var messenger: BinaryMessenger? = null

    // Messages from Flutter to this plugin will be sent to Unity via this:
    private val methodCallHandler = SendToUnity()
    // This lifecycle observer works around an issue with Unity freezing sometimes when
    // the app is brought back into foreground
    private val resumeUnityOnActivityResume = ResumeUnityOnActivityResume()
    // This is just so we can unregister the resumeUnityOnActivityResume lifecycle observer:
    private var activityPluginBinding: ActivityPluginBinding? = null

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        Log.d(logTag, "onAttachedToEngine")

        // Store the messenger reference, in case this is the main Flutter Engine and needs to setup a MethodChannel.
        // (do not create the MethodChannel here, it must only be created if an Activity is attached.
        // See initializeMethodChannel() and comments in onAttachedToActivity())
        messenger = flutterPluginBinding.binaryMessenger

        // Register a view factory
        // On the Flutter side, when we create a PlatformView with our unique identifier:
        // AndroidView(
        //    viewType: Constants.uniqueViewIdentifier,
        // )
        // the UnityViewFactory will be invoked to create a UnityPlatformView:
        flutterPluginBinding
            .platformViewRegistry
            .registerViewFactory(
                uniqueIdentifier,
                UnityViewFactory()
            )
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        Log.d(logTag, "onDetachedFromEngine")
        removeMethodChannel()
        messenger = null
    }


    private fun initializeMethodChannel() {
        if(channel == null) {
            messenger?.let { messenger ->
                this.channel = MethodChannel(messenger, uniqueIdentifier).apply {
                    setMethodCallHandler(methodCallHandler)
                }

                // Register the method channel with SendToFlutter static class (which is called by Unity)
                // so messages from Unity to this plugin can be forwarded on to Flutter
                SendToFlutter.methodChannel = channel
            } ?: run {
                // According to the Flutter lifecycle, onAttachedToEngine is called first and this shouldn't happen.
                Log.e(logTag, "BinaryMessenger is null. MethodChannel not created.")
            }
        }
    }

    private fun removeMethodChannel() {
        if (SendToFlutter.methodChannel == channel) {
            SendToFlutter.methodChannel = null
        }

        channel?.setMethodCallHandler(null)
        channel = null
    }

    // ActivityAware
    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        Log.d(logTag, "onAttachedToActivity")

        // Only setup the MethodChannel for the Flutter Engine that has the Activity attached.
        // This is important to avoid issues with other plugins creating additional background Flutter engines
        // See https://github.com/learntoflutter/flutter_embed_unity/issues/35
        initializeMethodChannel()

        // UnityPlayerSingleton needs the activity which will be received in onAttachedToActivity
        // so that the UnityPlayer can be created
        UnityPlayerSingleton.flutterActivity = binding.activity

        // See comments on ResumeActivityOnActivityResume for explanation of why this is needed
        (binding.lifecycle as HiddenLifecycleReference)
            .lifecycle
            .addObserver(resumeUnityOnActivityResume)
        // So we can remove the observer later on detach
        activityPluginBinding = binding
    }

    // ActivityAware
    override fun onDetachedFromActivityForConfigChanges() {
        Log.w(
            logTag, "onDetachedFromActivityForConfigChanges - this means the Flutter activity " +
                    "for your app was destroyed to process a configuration change. This scenario can lead " +
                    "to unexpected behaviour or crashes. You may be able to prevent configuration " +
                    "changes causing the activity to be destroyed by adding values to the android:configChanges " +
                    "attribute for your main activity in your app's AndroidManifest.xml. For example, if this " +
                    "happened on orientation change, add orientation|keyboardHidden|screenSize|screenLayout to " +
                    "android:configChanges. See " +
                    "https://developer.android.com/guide/topics/resources/runtime-changes#restrict-activity " +
                    "for more information"
        )
        UnityPlayerSingleton.flutterActivity = null
        // Remove the lifecycle observer
        (activityPluginBinding?.lifecycle as? HiddenLifecycleReference)
            ?.lifecycle
            ?.removeObserver(resumeUnityOnActivityResume)

        // The new activity that will be created for these config changes gets attached to the exact same Flutter Engine.
        // The BinaryMessenger and MethodChannel stay the same.
    }

    // ActivityAware
    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        Log.d(logTag, "onReattachedToActivityForConfigChanges")
        UnityPlayerSingleton.flutterActivity = binding.activity

        // See comments on ResumeActivityOnActivityResume for explanation of why this is needed
        (binding.lifecycle as HiddenLifecycleReference)
            .lifecycle
            .addObserver(resumeUnityOnActivityResume)
        // So we can remove the observer later on detach
        activityPluginBinding = binding

        // If for some unlikely reason the MethodChannel doesn't exist. Create it like in onAttachedToActivity.
        if (channel == null) {
            initializeMethodChannel()
            Log.d(logTag, "Initialized MethodChannel in onReattachedToActivityForConfigChanges, this shouldn't normally happen.")
        }
    }

    // ActivityAware
    override fun onDetachedFromActivity() {
        Log.i(logTag, "onDetachedFromActivity - the Flutter activity has been detached, unloading unity")
        // Destroying Unity is important - it prevents the app from crashing if the user exits the app
        // using the Android back button, then re-opens the app and navigates back to a screen with Unity
        // (see https://github.com/learntoflutter/flutter_embed_unity/issues/39)
        UnityPlayerSingleton.getInstance()?.destroy()
        UnityPlayerSingleton.flutterActivity = null
        // Remove the lifecycle observer
        (activityPluginBinding?.lifecycle as? HiddenLifecycleReference)
            ?.lifecycle
            ?.removeObserver(resumeUnityOnActivityResume)
        
        activityPluginBinding = null

        // channel is now invalid because the Activity is gone
        removeMethodChannel()
    }
}
