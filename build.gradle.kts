import java.net.Socket

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "1.9.25"
}

group = "com.saldi"
version = "0.0.1-SNAPSHOT"
description = "Learning idea"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5") // JSON serialization

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:elasticsearch")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// ----------------- Docker start task -----------------

tasks.register("startDocker") {
    doLast {
        val postgresRunning = exec {
            isIgnoreExitValue = true
            commandLine("bash", "-c", "docker ps --filter 'name=ecom-postgres' --filter 'status=running' -q")
        }.exitValue == 0

        if (!postgresRunning) {
            println("Starting Postgres...")
            exec { commandLine("docker", "compose", "up", "-d", "postgres") }
        } else {
            println("Postgres already running.")
        }

        val mongoRunning = exec {
            isIgnoreExitValue = true
            commandLine("bash", "-c", "docker ps --filter 'name=ecom-mongo' --filter 'status=running' -q")
        }.exitValue == 0

        if (!mongoRunning) {
            println("Starting MongoDB...")
            exec { commandLine("docker", "compose", "up", "-d", "mongodb") }
        } else {
            println("MongoDB already running.")
        }
    }
}

tasks.named("bootRun") {
    dependsOn("startDocker")
}


tasks.named("bootRun") {
    dependsOn("startDocker")
}
