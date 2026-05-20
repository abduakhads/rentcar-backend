# RentCar - Car Rental Reservation System

RentCar is a robust RESTful API built with Spring Boot, designed to manage car rental operations. It provides a complete solution for car management, member registration, reservations, and location-based searches, secured with JWT authentication.

## 🚀 Features

- **Car Management:** Track car details, status (Available, Maintenance, Rented), and transmission types.
- **Advanced Search:** Find available cars based on location, dates (checking for overlap with existing reservations), and other criteria.
- **Reservation System:** Seamless booking process including extra services (e.g., GPS, child seats) and automatic total price calculation.
- **Member Management:** Secure registration and profile management.
- **JWT Security:** Stateless authentication using JSON Web Tokens.
- **API Documentation:** Interactive documentation using Swagger UI.
- **Data Validation:** Strict validation for requests and consistent error handling.

## 🛠 Tech Stack

- **Language:** Java 17
- **Framework:** Spring Boot 3.5.7
- **Security:** Spring Security & JWT (JJWT)
- **Database:** PostgreSQL (Spring Data JPA / Hibernate)
- **Object Mapping:** MapStruct 1.6.3
- **Utility:** Lombok, Apache Commons Lang3
- **API Documentation:** SpringDoc OpenAPI 2.8.4
- **Build Tool:** Gradle

## 📋 Prerequisites

- **JDK 17** or higher
- **PostgreSQL** (Local or Cloud instance)
- **Gradle** (Wrapper included in the project)

## ⚙️ Configuration

The application uses environment variables for sensitive configuration. You can provide these in a `.env` file in the project root or directly in your environment.

### Required Environment Variables

| Variable | Description |
| :--- | :--- |
| `SPRING_DATASOURCE_URL` | PostgreSQL connection URL (e.g., `jdbc:postgresql://host:port/dbname`) |
| `JWT_SECRET` | Secret key for signing JWTs (Base64 encoded string recommended) |
| `JWT_EXPIRATION` | JWT expiration time in milliseconds (e.g., `86400000` for 24 hours) |
| `CORS_ALLOWED_ORIGINS` | Comma-separated list of allowed origins for CORS |

## 🏃 Getting Started

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/RentCar.git
   cd RentCar
   ```

2. **Configure the database:**
   Ensure PostgreSQL is running and update your environment variables or create a `.env` file.

3. **Build the project:**
   ```bash
   ./gradlew build
   ```

4. **Run the application:**
   ```bash
   ./gradlew bootRun
   ```

The server will start at `http://localhost:8080`.

## 📖 API Documentation

Once the application is running, you can access the interactive Swagger UI at:
`http://localhost:8080/swagger-ui.html`

The raw OpenAPI specification is available at:
`http://localhost:8080/v3/api-docs`

## 🏗 Project Structure

```text
src/main/java/com/example/
├── config/             # Configuration (Security, OpenAPI, Exceptions)
├── controllers/        # REST Controllers
├── dto/                # Data Transfer Objects
├── mappers/            # MapStruct Mappers
├── models/             # JPA Entities
├── repositories/       # Spring Data JPA Repositories
└── services/           # Business Logic Interfaces and Implementations
```

## 🧪 Testing

The project includes integration tests that use a real database context (with automatic transaction rollback).

Run all tests:
```bash
./gradlew test
```

