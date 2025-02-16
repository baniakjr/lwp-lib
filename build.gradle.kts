plugins {
    kotlin("jvm") version "2.1.0"
}

group = "baniakjr"
version = "1.1"

repositories {
    mavenCentral()
}

java {
    withSourcesJar()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation(kotlin("reflect"))
    testImplementation(libs.assertj)
    testImplementation(libs.junit.params)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}