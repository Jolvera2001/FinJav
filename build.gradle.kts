plugins {
    java
    application
    kotlin("jvm") version "2.1.10"
    id("org.javamodularity.moduleplugin") version "1.8.15"
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("org.beryx.jlink") version "2.25.0"
    id("io.freefair.lombok") version "8.14"
}

kotlin {
    jvmToolchain(21)
}

group = "dev.jolvera"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val junitVersion = "5.12.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainModule.set("dev.jolvera.finjav")
    mainClass.set("dev.jolvera.finjav.HelloApplication")
}

javafx {
    version = "21.0.6"
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.web", "javafx.swing")
}

dependencies {
    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")

    // javafx related
    implementation("org.controlsfx:controlsfx:11.2.1")
    implementation("net.synedra:validatorfx:0.6.1") {
        exclude(group = "org.openjfx")
    }
    implementation("org.kordamp.ikonli:ikonli-javafx:12.3.1")
    implementation("eu.hansolo:tilesfx:21.0.9") {
        exclude(group = "org.openjfx")
    }
    // validation
    implementation("jakarta.validation:jakarta.validation-api:3.1.1")
    implementation("org.hibernate.validator:hibernate-validator:9.0.1.Final")
    implementation("org.mindrot:jbcrypt:0.4")

    // DB
    implementation(platform("org.jdbi:jdbi3-bom:3.49.5"))
    implementation("org.jdbi:jdbi3-core")
    // implementation("org.jdbi:jdbi3-kotlin:3.49.5")
    // implementation("org.jdbi:jdbi3-kotlin-sqlobject:3.49.5")
    implementation("org.jdbi:jdbi3-sqlobject")
    implementation("org.jdbi:jdbi3-sqlite")
    implementation("org.xerial:sqlite-jdbc:3.47.1.0")

    // DI
    implementation("com.google.dagger:dagger:2.57")
    annotationProcessor("com.google.dagger:dagger-compiler:2.57")
    // runtimeOnly("io.insert-koin:koin-core:4.1.0")

    // testing
    testImplementation("org.jdbi:jdbi3-testing:3.49.5")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jlink {
    imageZip.set(layout.buildDirectory.file("/distributions/app-${javafx.platform.classifier}.zip"))
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    launcher {
        name = "app"
    }
}
