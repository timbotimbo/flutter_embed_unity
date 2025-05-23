## 1.3.0-beta.1

23 May 2025

* Beta release of support for Unity 6000.0 LTS

Thanks to [@timbotimbo](https://github.com/timbotimbo) for creating the Android platform implementation for Unity 6

#### Steps to migrate from Unity 2022.3 to Unity 6000.0:
* If you are using Android, add the following additional dependency to your pubspec.yaml:

```yaml
dependencies:
  ...
  # Add this for Unity 6000.0 support on Android:
  flutter_embed_unity_6000_0_android: ^1.2.1-beta.1  # (Use the latest available)
```
* If you are using Android, upgrade your android project's Gradle and AGP to match or exceed [those used by Unity 6000.0](https://docs.unity3d.com/6000.0/Documentation/Manual/android-gradle-overview.html) and update your NDK version to 27.2.12479018 or higher
* [Migrate your Unity project to Unity 6000.0](https://docs.unity3d.com/6000.0/Documentation/Manual/upgrade-project.html)
* Update your Unity project's Flutter export scripts by importing the new `flutter_embed_unity_6000_0.unitypackage` asset from [releases on Github](https://github.com/learntoflutter/flutter_embed_unity/releases)
* Export your Unity project to your Flutter project as before, using the new Unity 6 export scripts
* If you encounter any build errors, go through the project setup steps in the README again (this has been updated for Unity 6)


## 1.2.7

22 May 2025

* Updated example project to support Flutter 3.29, Gradle 8 Java 11. Bump flutter_embed_unity_2022_3_android to 1.1.3 for the same.
* Unity export fixed to prevent duplicate namespace being added to `unityLibrary/build.gradle` when using latest version of Unity 2022.3 ([#36](https://github.com/learntoflutter/flutter_embed_unity/pull/36) thanks [@timbotimbo](https://github.com/timbotimbo)).
* Updates to README with alternative gradle modification syntax for Kotlin DSL ([#37](https://github.com/learntoflutter/flutter_embed_unity/pull/37) thanks [@timbotimbo](https://github.com/timbotimbo))
* Unity export improved to automatically remove ndkPath from `unityLibrary/build.gradle` to prevent build error using latest version with flutter 3.29

To upgrade to the latest Unity export build script for Android, either manually replace the contents of your Unity project's `Assets/FlutterEmbed/Editor/ProjectExporterAndroid.cs` with the [content from the 1.2.7 release branch on Github](https://github.com/learntoflutter/flutter_embed_unity/blob/rel/1.2.7/example_unity_2022_3_project/Assets/FlutterEmbed/Editor/ProjectExporterAndroid.cs), or grab the `flutter_embed_unity_2022_3.unitypackage` asset from [1.2.7 release](https://github.com/learntoflutter/flutter_embed_unity/releases) and re-import into your Unity project


## 1.2.6

21 May 2025

Production release of 1.2.6: Destroy UnityPlayer when the main activity is detached from the plugin to prevent crash when re-opening the app. Fixes [#39](https://github.com/learntoflutter/flutter_embed_unity/issues/39)


## 1.2.6-beta.2

27 March 2025

* Bump flutter_embed_unity_2022_3_android to 1.1.2-beta.2 to fix: ~~Unload~~ Destroy UnityPlayer when the main activity is detached from the plugin to prevent crash when re-opening the app. Fixes [#39](https://github.com/learntoflutter/flutter_embed_unity/issues/39) in more scenarios (including when EmbedUnity widget is not at the bottom of the route stack)


## 1.2.6-beta.1

26 March 2025

* Bump flutter_embed_unity_2022_3_android to 1.1.2-beta.1 to fix: Unload UnityPlayer when the main activity is detached from the plugin to prevent crash when re-opening the app. Fixes [#39](https://github.com/learntoflutter/flutter_embed_unity/issues/39)


## 1.2.5

26 Feb 2025

* Updated README with more up to date advice regarding Gradle and AGP (see issue [#38](https://github.com/learntoflutter/flutter_embed_unity/issues/38) - thanks [@timbotimbo](https://github.com/timbotimbo))


## 1.2.4

23 Dec 2024

* Updated README with further details on issue [#21](https://github.com/learntoflutter/flutter_embed_unity/issues/21): workaround available for AR when using Flutter 3.22+ on Android API 32 or earlier (thanks [@timbotimbo](https://github.com/timbotimbo))


## 1.2.3

2 Aug 2024

* Lowered Dart SDK constraint to 2.18+ for compatibility with Flutter 3.7


## 1.2.2

31 July 2024

* ~~Lowered Dart SDK constraint to 2.18+~~ Fixed in 1.2.3


## 1.2.1

22 July 2024

* Updated README to note issue [#21](https://github.com/learntoflutter/flutter_embed_unity/issues/21): AR not supported when using Flutter 3.22 on Android API 32 or less (thanks [@timbotimbo](https://github.com/timbotimbo))

## 1.2.0

4 July 2024

> NOTE: To take advantage of the new automated steps for iOS Unity export, you will need to re-import the latest flutter_embed_unity_2022_3.unitypackage found in [Releases](https://github.com/learntoflutter/flutter_embed_unity/releases) - you only need the changes in `FlutterEmbed/Editor/ProjectExporterIos.cs`
>
> Alternatively, you can simply modify your existing copy of `FlutterEmbed/Editor/ProjectExporterIos.cs` according to [this commit](https://github.com/learntoflutter/flutter_embed_unity/commit/c393efc0e9fbc589e2e4c1045e52d5335830895a)

* After exporting Unity project for iOS, the manual steps for changing the membership of the Data folder to Unity Framework and adding `-Wl,-U,_FlutterEmbedUnityIos_sendToFlutter` to Other Linker Flags in Xcode is no longer required (thanks @timbotimbo). See note above.


## 1.1.1

16 May 2024

* Updated README to add Flutter 3.22 and later to the list of supported versions (3.22 fixes [#12](https://github.com/learntoflutter/flutter_embed_unity/issues/12) and [#14](https://github.com/learntoflutter/flutter_embed_unity/issues/14))


## 1.1.0

29 March 2024

* New Unity input system touches works on Android
* Updated readme to note Unity 2022.3.21 or later will be required on iOS from 1st May [to comply with App Store privacy manifest requirements](https://forum.unity.com/threads/apple-privacy-manifest-updates-for-unity-engine.1529026/)


## 1.1.0-beta1

13 March 2024

* New Unity input system touches works on Android


## 1.0.4

4 March 2024

* Updated README to note that all Unity versions between 2022.3.10 and 2022.3.18 are not supported with Android 8 or earlier due to [#15](https://github.com/learntoflutter/flutter_embed_unity/issues/15)


## 1.0.3

3 March 2024

* Updated README to note that Flutter 3.16.x and 3.19.x are not supported due to [Flutter #141068](https://github.com/flutter/flutter/issues/141068) and [Flutter #142952](https://github.com/flutter/flutter/issues/142952) (tracked in [flutter_embed_unity #14](https://github.com/learntoflutter/flutter_embed_unity/issues/14))


## 1.0.2

10 January 2024

* Updated README to note limitation of [#12](https://github.com/learntoflutter/flutter_embed_unity/issues/12)


## 1.0.1

20 November 2023

* Update Android and iOS platform dependencies to:
  * Fix iOS issue [#10](https://github.com/learntoflutter/flutter_embed_unity/issues/10)
  * Minor change to Android debug logs
* Updated README with info about [#9](https://github.com/learntoflutter/flutter_embed_unity/issues/9) (2022.3.9 not compatible with Xcode 15)
* Upgrade example Unity project version to 2022.3.13 to fix [#9](https://github.com/learntoflutter/flutter_embed_unity/issues/9)


## 1.0.0

12 October 2023

* First production release
* Added `EmbedUnityPreferences` to allow setting Unity message listening behaviour (see the README)


## 0.0.8-beta

3 October 2023

* Update Android and iOS platform dependencies to:
  * Removed green placeholder when Unity is detached from `EmbedUnity`


## 0.0.7-beta

28 September 2023

* Update Android and platform interface dependency to:
  * Fix [issue #5](https://github.com/learntoflutter/flutter_embed_unity/issues/5) (Unity freezing on hot reload and widget rebuild)


## 0.0.6-beta

27 September 2023

* Update iOS platform dependency to:
  * Fix [issue #6](https://github.com/learntoflutter/flutter_embed_unity/issues/6)
  * Fix iOS platform package dependency name in iOS example app


## 0.0.5-beta

* Update iOS platform dependency to:
  * Fix [issue #3](https://github.com/learntoflutter/flutter_embed_unity/issues/3): plugin not working when R8 / minification enabled on Android


## 0.0.4-beta

* Update dependencies


## 0.0.3-beta

* Minor changes to the README


## 0.0.2-beta

* **Breaking change:** due to a change in namespace, you MUST upgrade the `SendToFlutter.cs` script in your Unity project to use the new version from [the v0.0.2-beta release assets flutter_embed_unity_2022_3.unitypackage](https://github.com/learntoflutter/flutter_embed_unity/releases). Alternatively you can review [the source for SendToFlutter.cs](https://github.com/learntoflutter/flutter_embed_unity/blob/main/example_unity_2022_3_project/Assets/FlutterEmbed/SendToFlutter/SendToFlutter.cs) and make the change to the `AndroidJavaClass` package namespace manually.


## 0.0.1-beta

* Initial beta release