plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.moaz.rafiq"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.moaz.rafiq"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2024.12.01"))
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // حساب مواقيت الصلاة والقبلة
    implementation("com.batoulapps.adhan:adhan:1.2.1")

    // تحديد موقع الهاتف
    implementation("com.google.android.gms:play-services-location:21.3.0")

    // تشغيل القرآن والأذان
    implementation("androidx.media3:media3-exoplayer:1.5.1")

    debugImplementation("androidx.compose.ui:ui-tooling")
}
