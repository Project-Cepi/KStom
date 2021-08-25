plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.5.30"
    // Kotlinx serialization for any data format
    kotlin("plugin.serialization") version "1.4.21"
    // Shade the plugin
    id("com.github.johnrengelman.shadow") version "7.0.0"
    // Allow publishing
    `maven-publish`

    // Apply the application plugin to add support for building a jar
    java
    // Dokka documentation w/ kotlin
    id("org.jetbrains.dokka") version "1.5.0"
}


repositories {
    // Use mavenCentral
    mavenCentral()

    maven(url = "https://jitpack.io")
    maven(url = "https://repo.spongepowered.org/maven")
    maven(url = "https://repo.velocitypowered.com/snapshots/")
}


dependencies {

    // Use the Kotlin JDK 8 standard library.
    compileOnly(kotlin("stdlib", "1.5.0"))

    // Use the Kotlin reflect library.
    compileOnly(kotlin("reflect", "1.5.0"))

    // Use the JUpiter test library.
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")

    // Add support for kotlinx courotines
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")

    // Compile Minestom into project
    compileOnly("com.github.Minestom", "Minestom", "fa07d861a6")
    compileOnly("com.github.jglrxavpok", "Hephaistos", "1.1.8")

    // import kotlinx serialization
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")

    // Add MiniMessage
    implementation("net.kyori:adventure-text-minimessage:4.1.0-SNAPSHOT")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

configurations {
    testImplementation {
        extendsFrom(configurations.compileOnly.get())
    }
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveBaseName.set("kstom")
        mergeServiceFiles()
        minimize()

    }

    test { useJUnitPlatform() }

    build { dependsOn(shadowJar) }

}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
compileKotlin.kotlinOptions {
    freeCompilerArgs = listOf("-Xinline-classes", "-Xopt-in=kotlin.RequiresOptIn")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.properties["group"] as? String?
            artifactId = project.name
            version = project.properties["version"] as? String?

            from(components["java"])
        }
    }
}
sourceSets.create("demo") {
    java.srcDir("src/demo/java")
    java.srcDir("build/generated/source/apt/demo")
    resources.srcDir("src/demo/resources")
    compileClasspath += sourceSets.main.get().output + sourceSets.main.get().compileClasspath
    runtimeClasspath += sourceSets.main.get().output + sourceSets.main.get().runtimeClasspath
}