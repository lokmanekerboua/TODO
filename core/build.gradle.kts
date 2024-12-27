import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.google.dagger.hilt)
    alias(libs.plugins.kotlinx.serialization)
}

val projectJavaVersion = rootProject.extra["projectJavaVersion"] as JavaVersion
val projectMinSdkVersion = rootProject.extra["projectMinSdkVersion"] as Int
val projectCompileSdkVersion = rootProject.extra["projectCompileSdkVersion"] as Int
val projectTargetSdkVersion = rootProject.extra["projectTargetSdkVersion"] as Int
val projectIsMinifyEnabled = rootProject.extra["projectIsMinifyEnabled"] as Boolean

android {
    namespace = "me.lokmvne.core"
    compileSdk = projectCompileSdkVersion

    defaultConfig {
        minSdk = projectMinSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val constantsFile = rootProject.file("projectconstants.properties")
        val properties = Properties()
        properties.load(constantsFile.inputStream())
        val baseUrl = properties.getProperty("base_url") ?: ""
        buildConfigField("String", "base_url", "\"baseUrl\"")
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
        buildConfig = true
        compose = true
    }
}

dependencies {

    implementation(project(":common"))
    implementation(project(":compose"))

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

    //viewmodel
    implementation(libs.androidx.viewmodel.compose)
    implementation(libs.androidx.viewmodel.ktx)

    //coil
    implementation(libs.io.coil)

    //Dagger - Hilt
    implementation(libs.google.dagger.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.google.dagger.hilt.android.compiler)
    ksp(libs.androidx.dagger.hilt.compiler)

    //splash screen
    implementation(libs.androidx.core.splashscreen)

    //Retrofit
    implementation(libs.squareup.retrofit2)
    implementation(libs.squareup.retrofit2.gson)
    //Okhttp
    implementation(libs.squareup.okhttp3.logging.interceptor)
    implementation(libs.okhttp)

    //Room
    implementation(libs.androidx.room)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)

    //dataStore
    implementation(libs.androidx.datastore)

    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
}