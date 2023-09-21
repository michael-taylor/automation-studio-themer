plugins {
    kotlin("multiplatform") version "1.9.0"
    kotlin("plugin.serialization") version "1.8.21"
}

group = "city.atomic"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    // This project only supports Windows (Automation Studio itself is Windows-only)
    val nativeTarget = mingwX64("native")

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "main"
                runTask?.run {
                    val args = providers.gradleProperty("runArgs")
                    argumentProviders.add(CommandLineArgumentProvider {
                        args.orNull?.split(' ') ?: emptyList()
                    })
                }
            }
        }
    }
    sourceSets {
        val nativeMain by getting {
            dependencies {
                implementation("io.github.pdvrieze.xmlutil:serialization:0.86.1")
                implementation("com.github.ajalt.clikt:clikt:4.2.0")
                implementation("com.squareup.okio:okio:3.5.0")
            }
        }
        val nativeTest by getting
    }
}
