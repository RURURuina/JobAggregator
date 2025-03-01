// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        // for safe args&navigation
        classpath(libs.navigation.safe.args.gradle.plugin)
    }
}
plugins {
    id("com.android.application") version "8.5.2" apply false
    id("org.jetbrains.kotlin.android") version "2.0.21" apply false
    id("convention.detekt")
    id("com.google.devtools.ksp") version "2.0.21-1.0.26" apply false
}
