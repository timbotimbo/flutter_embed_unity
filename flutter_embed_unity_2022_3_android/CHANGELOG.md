## 1.1.4

24 June 2025

* Merged [#45](https://github.com/learntoflutter/flutter_embed_unity/pull/45): fixes touch events for Unity 6
* Merged [#46](https://github.com/learntoflutter/flutter_embed_unity/pull/46): fixes messages from Unity not arriving in Flutter when using other plugins which create background Flutter engines


## 1.1.3

22 May 2025

Updated example project to support Flutter 3.29, Gradle 8 Java 11, updated native plugin project's gradle to use Kotlin DSL, Java 11, Gradle 8.9, AGP 8.7, compile SDK 35


## 1.1.2

21 May 2025

Production release of 1.1.2: Destroy UnityPlayer when the main activity is detached from the plugin to prevent crash when re-opening the app. Fixes [#39](https://github.com/learntoflutter/flutter_embed_unity/issues/39)


## 1.1.2-beta.2

27 March 2025

* ~~Unload~~ Destroy UnityPlayer when the main activity is detached from the plugin to prevent crash when re-opening the app. Fixes [#39](https://github.com/learntoflutter/flutter_embed_unity/issues/39) in more scenarios (including when EmbedUnity widget is not at the bottom of the route stack)


## 1.1.2-beta.1

26 March 2025

* Unload UnityPlayer when the main activity is detached from the plugin to prevent crash when re-opening the app. Fixes [#39](https://github.com/learntoflutter/flutter_embed_unity/issues/39)


## 1.1.1

31 July 2024

* Lowered Dart SDK constraint to 2.18+


## 1.1.0

29 March 2024

* New Unity input system touches works on Android


## 1.1.0-beta1

13 March 2024

* New Unity input system touches works on Android


## 1.0.1

20 November 2023

* Minor change to debug logs


## 1.0.0

12 October 2023

* First production release


## 0.0.7-beta

3 October 2023

* Removed green placeholder when Unity is detached from `EmbedUnity`


## 0.0.6-beta

28 September 2023

* Fix [issue #5](https://github.com/learntoflutter/flutter_embed_unity/issues/5) (Unity freezing on hot reload and widget rebuild)


## 0.0.5-beta

* Fix [issue #3](https://github.com/learntoflutter/flutter_embed_unity/issues/3): plugin not working when R8 / minification enabled on Android


## 0.0.4-beta

* Update dependencies


## 0.0.3-beta

* Minor changes to the README


## 0.0.2-beta

* **Breaking change:** due to a change in namespace, you MUST upgrade the `SendToFlutter.cs` script in your Unity project to use the new version from [the v0.0.2-beta release assets flutter_embed_unity_2022_3.unitypackage](https://github.com/learntoflutter/flutter_embed_unity/releases). Alternatively you can review [the source for SendToFlutter.cs](https://github.com/learntoflutter/flutter_embed_unity/blob/main/example_unity_2022_3_project/Assets/FlutterEmbed/SendToFlutter/SendToFlutter.cs) and make the change to the `AndroidJavaClass` package namespace manually.


## 0.0.1-beta

* Initial beta release