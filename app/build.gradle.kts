plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "site.devflare.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "site.devflare.app"
        minSdk = 26
        targetSdk = 35
        versionCode = 3
        versionName = "0.1.2"
        vectorDrawables.useSupportLibrary = true
        ndk {
            abiFilters += listOf("armeabi-v7a", "arm64-v8a")
        }
        resourceConfigurations += "en"
    }

    signingConfigs {
        create("upload") {
            storeFile = rootProject.file("keystore/devflare-upload.p12")
            storePassword = "devflare-sideload"
            keyAlias = "upload"
            keyPassword = "devflare-sideload"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            signingConfig = signingConfigs.getByName("upload")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        debug {
            applicationIdSuffix = ""
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/*.version"
            excludes += "DebugProbesKt.bin"
            excludes += "kotlin/**"
        }
    }

    lint {
        checkReleaseBuilds = false
        abortOnError = false
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.okhttp)
    debugImplementation(libs.androidx.compose.ui.tooling)
}

tasks.register("packageSideload") {
    group = "distribution"
    description = "Copy the signed release APK to releases/DevFlare.apk"
    dependsOn("assembleRelease")
    doLast {
        val apk = layout.buildDirectory.file("outputs/apk/release/app-release.apk").get().asFile
        require(apk.exists()) { "Missing release APK at ${apk.absolutePath}" }
        val destDir = rootProject.file("releases")
        destDir.mkdirs()
        val dest = destDir.resolve("DevFlare.apk")
        apk.copyTo(dest, overwrite = true)
        println("Wrote ${dest.absolutePath} (${dest.length()} bytes)")
    }
}

afterEvaluate {
    tasks.named("assembleRelease").configure {
        finalizedBy("packageSideload")
    }
}
