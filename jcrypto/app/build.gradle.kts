val accpVersion = "2.2.0"

plugins {
    application
    id("com.google.osdetector") version "1.7.0"
}

repositories {
    mavenCentral()
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")

    implementation("info.picocli:picocli:4.7.3")
    implementation("org.bouncycastle:bc-fips:1.0.2.3")
    implementation("software.amazon.cryptools:AmazonCorrettoCryptoProvider:$accpVersion:${osdetector.classifier}")

    annotationProcessor("info.picocli:picocli-codegen:4.7.3")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    // Define the main class for the application.
    mainClass.set("io.vakili.tutorial.jcrypto.cli.CLI")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

tasks.withType<JavaExec> {
    systemProperties(System.getProperties().toMap() as Map<String, Object>)
}