# Builder stage
FROM gradle:8.10.2-jdk21 AS builder
WORKDIR /app
COPY . .
RUN ./gradlew bootJar -x test --no-daemon

# Runtime stage
# Use Java 21 for runtime
# Use Java 21 for runtime
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]


