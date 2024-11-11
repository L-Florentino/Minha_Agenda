plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.minha_agenda"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.minha_agenda"
        minSdk = 29
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
    // AndroidX Libraries
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    implementation("com.airbnb.android:lottie:3.0.1")

    // Firebase Dependencies
    implementation("com.google.firebase:firebase-analytics-ktx:21.1.1") // versão mais recente
    implementation("com.google.firebase:firebase-auth-ktx:22.1.1")      // versão mais recente
    implementation("com.google.firebase:firebase-database-ktx:20.2.1")  // versão mais recente
    implementation("com.firebaseui:firebase-ui-database:8.0.0")         // Verifique compatibilidade com versões Firebase mais novas

    // Test Dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

