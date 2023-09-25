plugins {
    kotlin("multiplatform") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.0"
    distribution
}

group = "city.atomic"

version = "1.0.3"

repositories { mavenCentral() }

val copyTemplates by tasks.registering(Copy::class) {
    val props = mapOf(
        "version" to version
    )
    from(projectDir.resolve("src/nativeMain/templates"))
    into(buildDir.resolve("generated-sources/templates/kotlin/main"))
    inputs.properties(props)
    expand(props)
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
                    argumentProviders.add(
                            CommandLineArgumentProvider { args.orNull?.split(' ') ?: emptyList() }
                    )
                }
            }
        }
    }
    sourceSets {
        val nativeMain by getting {
            kotlin.srcDirs(copyTemplates)
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
                implementation("io.github.pdvrieze.xmlutil:serialization:0.86.1")
                implementation("com.github.ajalt.clikt:clikt:4.2.0")
                implementation("com.squareup.okio:okio:3.5.0")
            }
        }
        val nativeTest by getting
    }
}

distributions {
    main {
        contents {
            from("$buildDir/bin/native/debugExecutable")
            from("themes")
        }
    }
}

tasks.named<Zip>("distZip") { dependsOn("linkDebugExecutableNative") }

