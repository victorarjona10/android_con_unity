// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
}
buildscript {
    repositories {
        google() // Incluye el repositorio de Google
        mavenCentral() // Incluye Maven Central
    }
}

//allprojects {
//    repositories {
//       // google() // Necesario para encontrar 'game-activity'
//        mavenCentral() // Incluye Maven Central como respaldo
//    }
//}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}