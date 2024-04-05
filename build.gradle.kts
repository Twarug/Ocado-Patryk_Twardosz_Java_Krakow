plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1" // For building fat-jar
}

group = "com.ocado"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1") // For JSON parsing
}
