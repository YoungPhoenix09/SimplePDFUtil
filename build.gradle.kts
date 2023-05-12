plugins {
    kotlin("jvm") version "1.8.20"
}

group = "org.paynefulapps"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.pdfbox:pdfbox:2.0.27")
    testImplementation(kotlin("test"))
}

sourceSets{
    create("test-integration") {
        kotlin.srcDirs("src/test-integration/kotlin", "src/test/kotlin")
        compileClasspath += sourceSets["main"].output + sourceSets["test"].output + sourceSets["test"].compileClasspath
        runtimeClasspath += sourceSets["main"].output + sourceSets["test"].output + sourceSets["test"].runtimeClasspath
    }
}

tasks.test {
    useJUnitPlatform()
}

task<Test>("test-integration") {
    useJUnitPlatform()
    description = "Run integration tests"
    group = "verification"
    testClassesDirs = sourceSets["test-integration"].output.classesDirs
    classpath = sourceSets["test-integration"].runtimeClasspath
}

kotlin {
    jvmToolchain(11)
}
