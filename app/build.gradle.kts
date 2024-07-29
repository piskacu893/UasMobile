plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.projek_uas"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.projek_uas"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("androidx.core:core:1.8.0")
    implementation("com.google.android.gms:play-services-maps:18.0.2")
    implementation("androidx.work:work-runtime:2.7.1")
    implementation("com.google.firebase:firebase-database:19.7.0")
    implementation("com.google.maps:google-maps-services:0.17.0")
    implementation("com.google.android.material:material:1.5.0")
    implementation("com.google.android.libraries.places:places:2.5.0")
    implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("com.google.android.libraries.places:places:2.7.0")
    implementation ("com.google.firebase:firebase-auth:21.1.0")
    implementation ("com.airbnb.android:lottie:3.7.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
