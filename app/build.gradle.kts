/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    id("it.unicam.cs.mpmgc.formula1.java-library-conventions")
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.13"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":api"))
}

javafx {
    version = "17.0.13"
    modules = listOf("javafx.controls", "javafx.fxml")
}

application {
    mainClass.set("it.unicam.cs.mpmgc.formula1.app.MainFX")
}
