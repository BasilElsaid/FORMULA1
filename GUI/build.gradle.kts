/*
 * This file was generated by the Gradle 'init' task.
 */

val javaFXVersion = "17.0.13"

plugins {
    id("it.unicam.cs.mpmgc.formula1.java-library-conventions")
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.14"
}

dependencies {
    implementation(project(":app"))
    implementation("org.openjfx:javafx-base:$javaFXVersion")
    implementation("org.openjfx:javafx-controls:$javaFXVersion")
    implementation("org.openjfx:javafx-graphics:$javaFXVersion")
    implementation("org.openjfx:javafx-fxml:$javaFXVersion") // If you use FXML
}

javafx {
    version = "17.0.13"
    modules = listOf("javafx.controls", "javafx.fxml")
}

application {
    // Define the main class for the application.
    applicationDefaultJvmArgs = listOf(
        "--module-path", "/Users/basil/Downloads/javafx-sdk-17.0.13/lib",
        "--add-modules", "javafx.controls,javafx.fxml"
    )
    mainClass.set("it.unicam.cs.mpmgc.formula1.utilities.MainFX")
}
