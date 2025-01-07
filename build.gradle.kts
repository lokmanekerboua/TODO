// Top-level build file where you can add configuration options common to all sub-projects/modules.

extra["projectJavaVersion"] = JavaVersion.VERSION_17
extra["projectMinSdkVersion"] = 28
extra["projectCompileSdkVersion"] = 35
extra["projectTargetSdkVersion"] = 35
extra["projectIsMinifyEnabled"] = false

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.google.devtools.ksp) apply false
    alias(libs.plugins.google.dagger.hilt) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.gms.google.services) apply false
}