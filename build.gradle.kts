plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.4.31"
    kotlin("plugin.serialization") version "1.4.10"
    `maven-publish`
    maven

    // Apply the application plugin to add support for building a jar
    java
}


repositories {
    // Use jcenter for resolving dependencies.
    jcenter()

    // Use mavenCentral
    maven(url = "https://repo1.maven.org/maven2/")
    maven(url = "https://repo.spongepowered.org/maven")
    maven(url = "https://libraries.minecraft.net")
    maven(url = "https://jitpack.io")
    maven(url = "https://jcenter.bintray.com/")
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation(kotlin("stdlib"))

    // Use the Kotlin reflect library.
    implementation(kotlin("reflect"))

    // Add support for kotlinx courotines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")

    // Compile Minestom into project
    implementation("com.github.Minestom:Minestom:df2ee23a7c")

    // OkHttp
    implementation("com.squareup.okhttp3", "okhttp", "4.9.0")

    // import kotlinx serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")

    // Use mworlza's canvas
    implementation("com.github.mworzala:canvas:bb1772580e")

    // Add Kyori Minestom implementation
    implementation("com.github.mworzala:adventure-platform-minestom:2e12f45b2e")
    implementation("net.kyori:adventure-text-minimessage:4.0.0-SNAPSHOT")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

kotlin {
    explicitApi()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()