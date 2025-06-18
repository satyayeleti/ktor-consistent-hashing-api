plugins {
    id("io.ktor.plugin") version "3.2.0"
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0" // ✅ Add this line
    application
}

group = "com.satya"
version = "0.0.1"

application {
    mainClass.set("com.satya.consistenthashing.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-simple:2.0.13")
    implementation("io.ktor:ktor-server-core:3.2.0")
    implementation("io.ktor:ktor-server-netty:3.2.0")
    implementation("io.ktor:ktor-server-content-negotiation:3.2.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.2.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("io.ktor:ktor-server-call-logging-jvm:3.2.0")
    testImplementation("io.ktor:ktor-server-tests:2.3.10")
    testImplementation(kotlin("test"))

}
