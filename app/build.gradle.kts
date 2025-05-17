plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.ksp)
    alias(libs.plugins.parcelize)
}

android {
    namespace = "com.fjlr.firebase"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.fjlr.firebase"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.auth.google)
    implementation(libs.firebase.firestore)
    implementation(libs.google.services)
    implementation(libs.firebase.storage)

    implementation(libs.picasso)

    implementation(libs.lottie)

    implementation(libs.corroutines.android)
    implementation(libs.corroutines.core)
    implementation(libs.viewmodel.ktx)
    implementation(libs.livedata.ktx)
    implementation(libs.runtime.ktx)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}