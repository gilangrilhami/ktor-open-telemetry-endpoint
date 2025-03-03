val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    alias(libs.plugins.ktor)
    kotlin("jvm").version("1.9.0")
    kotlin("plugin.serialization").version("2.0.0")
    id("com.google.protobuf").version("0.9.4")
}

group = "com.wolkk"
version = "0.0.1"

application {
    mainClass.set("com.wolkk.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral() 
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-cio:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-protobuf:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("com.google.protobuf:protobuf-java:3.19.4")
    implementation("com.google.protobuf:protobuf-java-util:3.19.4")
    testImplementation("io.ktor:ktor-server-test-host-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.19.4"
    }
    generatedFilesBaseDir = "$projectDir/src/generated"
    // Add additional configuration if needed
    // E.g., specifying proto files location:
    sourceSets {
        main {
            proto {
                srcDir("protos")
            }
        }
    }
}

ktor { fatJar { archiveFileName.set("fat.jar") } }