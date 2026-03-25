// swift-tools-version: 5.9
// The swift-tools-version declares the minimum version of Swift required to build this package.

// created based on https://docs.flutter.dev/packages-and-plugins/swift-package-manager/for-plugin-authors


import PackageDescription

let package = Package(
    name: "flutter_embed_unity_2022_3_ios",
    platforms: [
        .iOS(.v12),
    ],
    products: [
        // If the plugin name contains "_", replace with "-" for the library name.
        .library(name: "flutter-embed-unity-2022-3-ios", targets: ["flutter_embed_unity_2022_3_ios"])
    ],
    dependencies: [
        // Mentioned as "new in Flutter 3.41" 
        // .package(name: "FlutterFramework", path: "../FlutterFramework") // Flutter 3.41
    ],
    targets: [
        .target(
            name: "flutter_embed_unity_2022_3_ios",
            dependencies: [
                "UnityFramework"

                // Mentioned as "new in Flutter 3.41" 
                // .product(name: "FlutterFramework", package: "FlutterFramework")
            ],
            resources: [
                // If your plugin requires a privacy manifest
                // (e.g. if it uses any required reason APIs), update the PrivacyInfo.xcprivacy file
                // to describe your plugin's privacy impact, and then uncomment this line.
                // For more information, see:
                // https://developer.apple.com/documentation/bundleresources/privacy_manifest_files
                // .process("PrivacyInfo.xcprivacy"),

                // If you have other resources that need to be bundled with your plugin, refer to
                // the following instructions to add them:
                // https://developer.apple.com/documentation/xcode/bundling-resources-with-a-swift-package
            ]
        ),
        .binaryTarget(
            name: "UnityFramework",
            // SPM needs the version of the XCFramework stub which contains just a raw static 
            // library (.a). The static framework version is used when building with Cocoapods, 
            // which does not know how to handle this kind of static library
            path: "UnityFrameworkStubs/StaticLibrary/UnityFramework.xcframework"
        )
    ]
)
