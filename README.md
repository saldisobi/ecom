# Ecom Project

This is a personal project for learning Spring Boot, Kotlin, PostgreSQL, Flyway, and Docker integration.

---

## Prerequisites

Before running the project, make sure you have the following installed:

* [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
* [Gradle](https://gradle.org/install/) (optional, as the wrapper is included)
* [Docker](https://www.docker.com/get-started)

---

## Setup

1. Clone the repository:

```bash
git clone https://github.com/saldisobi/ecom.git
cd ecom
```

2. Make sure Docker is running on your machine.

3. The project includes a Gradle task to ensure PostgreSQL runs in Docker before the application starts. No additional setup is needed for the database.

---

## Running the Project

Use the Gradle wrapper to start the project:

```bash
./gradlew clean bootRun
```

What happens:

1. Checks if PostgreSQL is running on `localhost:5432`.
2. If not, starts the Postgres container using Docker Compose.
3. Waits until the database is ready.
4. Starts the Spring Boot application.

---

## API Endpoints

* **Register**: `POST /api/auth/register`
* **Login**: `POST /api/auth/login`

> Use the tokens returned from login for authorization in subsequent requests.

---

## Database

* PostgreSQL runs via Docker Compose.
* Flyway handles migrations automatically.
* Connection configured in `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecom_db
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.flyway.enabled=true
```

---

## Notes

* All scripts and Gradle tasks are self-contained; no need to manually start Docker unless you want.
* If you want to rebuild the Docker container completely:

```bash
docker compose build --no-cache
```

* After that, you can still run:

```bash
./gradlew clean bootRun
```

* Ensure your Docker containers are running if you restart your machine. The Gradle task handles this automatically.

---

## Testing

Run all tests using:

```bash
./gradlew test
```

---

## License

This project is personal and for learning purposes.
