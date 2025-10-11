# 🛍️ Ecom Platform — Kotlin + Spring Boot

A modular, event-driven e-commerce backend built with **Spring Boot**, **Kotlin**, and a modern microservices-ready architecture. This platform leverages polyglot persistence and asynchronous messaging to deliver scalable product management, user authentication, and real-time search capabilities.

> **Note:** This is a personal learning project exploring modern backend technologies and architectural patterns.

---

## 🎯 Architecture Overview

This platform demonstrates a hybrid data storage approach with event-driven synchronization:

- **Postgres** for transactional user data and authentication
- **MongoDB** for flexible product catalog management
- **Kafka** for asynchronous event streaming between services
- **Elasticsearch** for high-performance full-text search
- **Kibana** for search analytics and data visualization

---

## 🚀 Tech Stack

| Component | Purpose | Port |
|-----------|---------|------|
| **Spring Boot** | Core REST API service | 8080 |
| **Postgres** | User data, authentication, Flyway migrations | 5432 |
| **MongoDB** | Product catalog (flexible schema) | 27017 |
| **Mongo Express** | MongoDB web UI | 8081 |
| **Kafka + Zookeeper** | Event streaming (product → search sync) | 9092 / 2181 |
| **Elasticsearch** | Full-text product search engine | 9200 |
| **Kibana** | Search analytics & visualization | 5601 |

---

## 🧱 Project Modules

| Module | Responsibility |
|--------|----------------|
| **auth** | JWT-based user registration and authentication |
| **product** | Product CRUD operations with MongoDB persistence |
| **search** | Elasticsearch integration for product indexing & search |
| **kafka** | Event-driven synchronization between product and search services |

---

## 📋 Prerequisites

Before running the project, ensure you have the following installed:

- **[Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)** - Required for Spring Boot
- **[Gradle](https://gradle.org/install/)** - Optional (wrapper included in project)
- **[Docker](https://www.docker.com/get-started)** - For running infrastructure services

---

## ⚙️ Setup & Installation

### 1. Clone the Repository

```bash
git clone https://github.com/saldisobi/ecom.git
cd ecom
```

### 2. Ensure Docker is Running

Make sure Docker Desktop or Docker Engine is running on your machine.

### 3. Start Infrastructure Services

From the project root directory:

```bash
docker compose up -d
```

This command launches all infrastructure components:
- PostgreSQL (user database)
- MongoDB (product database)
- Mongo Express (database UI)
- Kafka & Zookeeper (event streaming)
- Elasticsearch (search engine)
- Kibana (analytics dashboard)

**Automated Setup:** The project includes a Gradle task that automatically checks if PostgreSQL is running on `localhost:5432`. If not, it starts the Postgres container using Docker Compose and waits until the database is ready before starting the application.

### 4. Run the Application

**Using Gradle Wrapper (Recommended):**
```bash
./gradlew clean bootRun
```

**What happens:**
1. Checks if PostgreSQL is running on `localhost:5432`
2. If not running, starts the Postgres container using Docker Compose
3. Waits until the database is ready
4. Starts the Spring Boot application

**Using IntelliJ IDEA:**
- Open the project
- Run the main Spring Boot application class

---

## 🔌 Verify Services

Once everything is running, verify all services are accessible:

| Service | Access URL | Description |
|---------|------------|-------------|
| API Endpoint | http://localhost:8080 | Main application REST API |
| Mongo Express | http://localhost:8081 | MongoDB web interface |
| Kibana Dashboard | http://localhost:5601 | Elasticsearch analytics |
| Elasticsearch API | http://localhost:9200/_cat/indices?v | Direct ES access |
| PostgreSQL | `localhost:5432` | Database connection |

---

## 🧩 API Examples

### 🔐 User Registration

```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "testuser@example.com",
    "password": "TestPassword123"
  }'
```

**Simplified Endpoint:**
```bash
POST /api/auth/register
```

### 🔑 User Login

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "TestPassword123"
  }'
```

**Simplified Endpoint:**
```bash
POST /api/auth/login
```

> **Important:** Save the JWT token returned from login to use for authorization in subsequent requests.

### 🛒 Create Product

```bash
curl -X POST http://localhost:8080/api/products \
  -H "Authorization: Basic YWRtaW46YWRtaW4=" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "iPhone 15",
    "description": "Latest Apple iPhone with A17 chip",
    "price": 1299.99,
    "category": "Electronics",
    "stock": 20,
    "popularity": 85
  }'
```

### 🔍 Search Products

```bash
curl -X GET "http://localhost:8080/api/search?term=iphone"
```

---

## 🗄️ Database Configuration

### PostgreSQL

- **Runs via Docker Compose** - Automatically managed
- **Flyway Migrations** - Handles schema changes automatically
- **Connection Details:**

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecom_db
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.flyway.enabled=true
```

### MongoDB

- **Schema-less Design** - Flexible product data model
- **Auto-sync to Elasticsearch** - Via Kafka events
- **Connection:** Managed through Spring Data MongoDB

---

## 🔎 Elasticsearch & Kibana Setup

1. **Access Kibana:** Navigate to http://localhost:5601
2. **Create Index Pattern:**
    - Go to Management → Stack Management → Index Patterns
    - Create pattern: `searchproduct*`
3. **Explore Data:**
    - Use the Discover tab to view indexed products
    - Build visualizations and dashboards for search analytics

**Direct Elasticsearch Access:** http://localhost:9200

---

## 🧪 Testing

Run all tests using Gradle:

```bash
./gradlew test
```

This executes:
- Unit tests
- Integration tests
- Repository tests

---

## 🧠 Key Features & Design Decisions

### Data Architecture
- **Polyglot Persistence:** Right database for the right job—Postgres for ACID compliance, MongoDB for schema flexibility
- **Event-Driven Sync:** Products automatically indexed to Elasticsearch via Kafka events
- **Schema Management:** Flyway handles Postgres migrations; MongoDB offers schema flexibility

### Security
- **JWT Authentication:** Secure token-based auth for user endpoints
- **Basic Auth:** Internal admin endpoints protected with Basic Authentication
- **Spring Security:** Comprehensive security configuration with role-based access

### Automation
- **Self-Contained Scripts:** All Gradle tasks check and start required services automatically
- **No Manual Docker Management:** The build system handles Docker containers for you

---

## 🔧 Additional Docker Commands

### Rebuild Docker Containers

If you need to completely rebuild the Docker containers:

```bash
docker compose build --no-cache
```

Then run the application as usual:

```bash
./gradlew clean bootRun
```

### Stop All Services

Shut down all Docker containers:

```bash
docker compose down
```

### Remove All Data

To stop containers and remove volumes (⚠️ **deletes all data**):

```bash
docker compose down -v
```

### Restart After Machine Reboot

If you restart your machine, ensure Docker containers are running. The Gradle task handles this automatically when you run:

```bash
./gradlew clean bootRun
```

---

## 📁 Project Structure

```
ecom-platform/
├── src/main/kotlin/
│   ├── auth/          # Authentication module
│   ├── product/       # Product management
│   ├── search/        # Elasticsearch integration
│   └── kafka/         # Event streaming
├── docker-compose.yml # Infrastructure definition
├── build.gradle.kts   # Build configuration
└── README.md
```

---

## 📝 Notes

- **Self-Contained:** All scripts and Gradle tasks are self-contained; no need to manually start Docker unless you want to
- **Automatic Service Management:** The Gradle build automatically checks for and starts required services
- **Database Migrations:** Flyway runs automatically on application startup
- **Hot Reload:** Changes to Kotlin code will trigger automatic recompilation

---

## 🚧 Roadmap

- [ ] Order management service
- [ ] Payment gateway integration
- [ ] Redis caching layer
- [ ] API rate limiting
- [ ] GraphQL API support
- [ ] Kubernetes deployment configs
- [ ] Comprehensive API documentation (Swagger/OpenAPI)

---

## 🧑‍💻 Author

**Sourabh Saldi**  
Senior Software Engineer | Berlin

---

## 📄 License

This project is personal and for learning purposes.

---

**Questions or Feedback?** Feel free to reach out or open an issue on [GitHub](https://github.com/saldisobi/ecom)!