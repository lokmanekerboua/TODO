import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.dagger.hilt)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.gms.google.services)
}

val projectJavaVersion = rootProject.extra["projectJavaVersion"] as JavaVersion
val projectMinSdkVersion = rootProject.extra["projectMinSdkVersion"] as Int
val projectCompileSdkVersion = rootProject.extra["projectCompileSdkVersion"] as Int
val projectTargetSdkVersion = rootProject.extra["projectTargetSdkVersion"] as Int
val projectIsMinifyEnabled = rootProject.extra["projectIsMinifyEnabled"] as Boolean

android {
    namespace = "me.lokmvne.auth"
    compileSdk = projectCompileSdkVersion

    defaultConfig {
        minSdk = projectMinSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val constantsFile = rootProject.file("projectconstants.properties")
        val properties = Properties()
        properties.load(constantsFile.inputStream())
        val firebaseId = properties.getProperty("firebase_id") ?: ""
        buildConfigField("String", "firebase_id", "\"$firebaseId\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = projectIsMinifyEnabled
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = projectJavaVersion
        targetCompatibility = projectJavaVersion
    }
    kotlinOptions {
        jvmTarget = projectJavaVersion.toString()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(project(":common"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //dataStore
    implementation(libs.androidx.datastore)

    //Dagger - Hilt
    implementation(libs.google.dagger.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.google.dagger.hilt.android.compiler)
    ksp(libs.androidx.dagger.hilt.compiler)

    //FireBase
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.analytics)
    implementation(libs.google.firebase.auth)

    //google credentials
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
}