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

tasks.register<Exec>("startDocker") {
    group = "docker"
    description = "Starts Postgres via Docker Compose if not running"

    doFirst {
        val isRunning = try {
            Socket("localhost", 5432).use { true }
        } catch (_: Exception) {
            false
        }

        if (!isRunning) {
            println("Starting Postgres container via Docker Compose...")
            commandLine("docker", "compose", "up", "-d", "postgres")
        } else {
            println("Postgres is already running on localhost:5432")
            commandLine("echo", "Skipping docker compose, already running")
        }
    }

    doLast {
        println("Waiting for Postgres to be ready...")
        exec {
            commandLine(
                "bash", "-c",
                "until docker exec ecom-postgres pg_isready -U postgres >/dev/null 2>&1; do sleep 1; done"
            )
        }
        println("Postgres is ready!")
    }
}

tasks.named("bootRun") {
    dependsOn("startDocker")
}
