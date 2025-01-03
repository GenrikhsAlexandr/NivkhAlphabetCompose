// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.daggerHiltAndroid) apply false
    alias(libs.plugins.kotlinKapt) apply false
    alias(libs.plugins.kotlinParcelize) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.detekt) apply true
}

tasks.register("detektAll") {
    dependsOn(subprojects.map { "${it.path}:detekt" })
}
