import com.android.build.api.dsl.ApplicationExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.fromTarget("11")
        languageVersion = KotlinVersion.fromVersion("2.2")
        apiVersion = KotlinVersion.fromVersion("2.2")
    }
}

// Room's schema export was configured under javaCompileOptions.annotationProcessorOptions, which
// only applies to javac/kapt. Under KSP the argument never arrived, so KSP emitted
// "Schema export directory was not provided to the annotation processor" on every build and
// app/schemas only ever contained version 1.
ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

extensions.configure<ApplicationExtension> {
    namespace = "cat.rubenzu03.catbrary"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.rubenzu03.catbrary"
        minSdk = 33
        targetSdk = 37
        versionCode = 2
        versionName = "2.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_KEY", "\"${project.findProperty("MY_API_KEY") ?: ""}\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    buildToolsVersion = "37.0.0"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.windowsize)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.volley)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.material3)
    // Replaces 14 hand-rolled vector drawables. Core only (not -extended): -extended adds tens of
    // thousands of methods, and this module ships with minify disabled.
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.coil.compose)
    ksp(libs.androidx.room.compiler)

}