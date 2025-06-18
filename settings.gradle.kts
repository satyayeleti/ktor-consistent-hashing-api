pluginManagement {
    repositories {
        gradlePluginPortal() // 🔧 Needed for ktor plugin
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

rootProject.name = "ktor-consistent-hashing-api"
