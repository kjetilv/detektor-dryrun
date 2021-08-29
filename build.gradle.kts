import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
    id("io.gitlab.arturbosch.detekt").version("1.18.0")
    `maven-publish`
}

group = "com.github.kjetilv.detektordryrun"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("io.gitlab.arturbosch.detekt:detekt-api:1.18.0-RC2")
    implementation("io.gitlab.arturbosch.detekt:detekt-metrics:1.18.0-RC2")

    testImplementation("io.gitlab.arturbosch.detekt:detekt-test:1.18.0-RC2")
    testImplementation("org.assertj:assertj-core:3.20.2")
    testImplementation("junit:junit:4.13.2")

//     Uncomment below line after doing a ./gradlew publishToMavenLocal :-)
//    detektPlugins("$group:$name:$version")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

detekt {
    source = files("src/main/java", "src/main/kotlin")
    config = files("detekt.yml")
    reports {
        html {
            enabled = true
            destination = file("build/reports/detekt.html")
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}
