plugins {
    kotlin("jvm") version "2.1.0"
    id("org.jetbrains.dokka") version "2.0.0"
    id("org.jetbrains.dokka-javadoc") version "2.0.0"
}

group = "baniakjr"
version = "1.2"

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

dokka {
    moduleName.set("LWP Lib")
    dokkaSourceSets.main {
        sourceLink {
            localDirectory.set(file("src/main/kotlin"))
            remoteUrl("https://github.com/baniakjr/lwp-lib/tree/main")
            remoteLineSuffix.set("#L")
        }
    }
}

tasks.register<Jar>("dokkaHtmlJar") {
    group = "docs-jar"
    description = "Create jar with htmldoc"
    dependsOn(tasks.dokkaGenerate)
    from(tasks.dokkaGenerateModuleHtml.flatMap { it.outputDirectory })
    archiveClassifier.set("html-docs")
}

tasks.register<Jar>("dokkaJavadocJar") {
    group = "docs-jar"
    description = "Create jar with javadoc"
    dependsOn(tasks.dokkaGenerate)
    from(tasks.dokkaGenerateModuleJavadoc.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}

tasks.build {
    dependsOn("dokkaJavadocJar")
    dependsOn("dokkaHtmlJar")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}