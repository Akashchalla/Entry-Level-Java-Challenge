# Employee Management REST API

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.10-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-8.10.2-blue.svg)](https://gradle.org/)
[![License](https://img.shields.io/badge/License-Educational-yellow.svg)]()

A RESTful API for managing employee information, built with Spring Boot. This project provides endpoints for viewing and creating employee records with automatic UUID generation and data validation.

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Usage Examples](#usage-examples)
- [Project Structure](#project-structure)
- [Testing](#testing)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)

## Features

- ✅ RESTful API design with proper HTTP methods
- ✅ In-memory data storage with pre-loaded mock data
- ✅ Automatic UUID generation for employees
- ✅ Comprehensive input validation
- ✅ Clean architecture (Controller → Service → Data layers)
- ✅ Detailed error handling with descriptive messages
- ✅ Code formatting with Spotless

## Prerequisites

- **Java 21** or higher
- **Gradle** (wrapper included)
- **curl** or **Postman** (for testing)

### Verify Java Installation

```bash
java -version
```

Expected output:
```
openjdk version "21" or higher
```

## Installation

1. **Clone the repository**

```bash
git clone <repository-url>
cd entry-level-java-challenge
```

2. **Make Gradle wrapper executable** (Mac/Linux only)

```bash
chmod +x gradlew
```

3. **Build the project**

```bash
./gradlew build
```

## Running the Application

### Start the Server

```bash
./gradlew bootRun
```

The application will start on **http://localhost:8080**

Wait for the following output:
```
Tomcat started on port 8080 (http) with context path ''
Started EntryLevelJavaChallengeApplication in X.XXX seconds
```

### Stop the Server

Press `Ctrl + C` in the terminal where the server is running.

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/v1/employee` | Retrieve all employees |
| `GET` | `/api/v1/employee/{uuid}` | Retrieve employee by UUID |
| `POST` | `/api/v1/employee` | Create a new employee |

### Base URL

```
http://localhost:8080/api/v1/employee
```

## Usage Examples

### 1. Get All Employees

**Request:**
```bash
curl http://localhost:8080/api/v1/employee
```

**Response:**
```json
[
  {
    "uuid": "58852996-9c72-4406-8286-f9b77b290e9f",
    "firstName": "John",
    "lastName": "Doe",
    "fullName": "John Doe",
    "salary": 75000,
    "age": 30,
    "jobTitle": "Software Engineer",
    "email": "john.doe@company.com",
    "contractHireDate": "2023-10-24T15:12:36.929351Z",
    "contractTerminationDate": null
  }
]
```

### 2. Get Employee by UUID

**Request:**
```bash
curl http://localhost:8080/api/v1/employee/{uuid}
```

Replace `{uuid}` with an actual employee UUID.

**Example:**
```bash
curl http://localhost:8080/api/v1/employee/58852996-9c72-4406-8286-f9b77b290e9f
```

**Response:**
```json
{
  "uuid": "58852996-9c72-4406-8286-f9b77b290e9f",
  "firstName": "John",
  "lastName": "Doe",
  "fullName": "John Doe",
  "salary": 75000,
  "age": 30,
  "jobTitle": "Software Engineer",
  "email": "john.doe@company.com",
  "contractHireDate": "2023-10-24T15:12:36.929351Z",
  "contractTerminationDate": null
}
```

**Error Response (404):**
```json
{
  "timestamp": "2025-10-23T15:16:32.625+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Employee not found with UUID: {uuid}"
}
```

### 3. Create New Employee

**Request:**
```bash
curl -X POST http://localhost:8080/api/v1/employee \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Alice",
    "lastName": "Johnson",
    "salary": 85000,
    "age": 29,
    "jobTitle": "Data Scientist",
    "email": "alice.johnson@company.com"
  }'
```

**Response (201 Created):**
```json
{
  "uuid": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "firstName": "Alice",
  "lastName": "Johnson",
  "fullName": "Alice Johnson",
  "salary": 85000,
  "age": 29,
  "jobTitle": "Data Scientist",
  "email": "alice.johnson@company.com",
  "contractHireDate": "2025-10-23T15:35:00.123456Z",
  "contractTerminationDate": null
}
```

**Error Response (400 Bad Request):**
```json
{
  "timestamp": "2025-10-23T15:16:32.625+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "First name is required"
}
```

## Request Body Schema

### Create Employee Request

| Field | Type | Required | Validation |
|-------|------|----------|------------|
| `firstName` | `String` | Yes | Cannot be empty |
| `lastName` | `String` | Yes | Cannot be empty |
| `email` | `String` | Yes | Cannot be empty |
| `salary` | `Integer` | Yes | Must be ≥ 0 |
| `age` | `Integer` | Yes | Must be between 18-120 |
| `jobTitle` | `String` | No | Optional |

### Auto-Generated Fields

- `uuid` - Automatically generated
- `fullName` - Concatenation of firstName and lastName
- `contractHireDate` - Set to current timestamp
- `contractTerminationDate` - null by default

## Project Structure

```
entry-level-java-challenge/
├── api/
│   └── src/
│       └── main/
│           └── java/
│               └── com/challenge/api/
│                   ├── controller/
│                   │   └── EmployeeController.java      # REST endpoints
│                   ├── service/
│                   │   └── EmployeeService.java         # Business logic
│                   ├── model/
│                   │   ├── Employee.java                # Interface
│                   │   └── EmployeeImpl.java            # Implementation
│                   ├── dto/
│                   │   └── CreateEmployeeRequest.java   # Request DTO
│                   └── EntryLevelJavaChallengeApplication.java
├── gradle/
├── gradlew
├── gradlew.bat
└── README.md
```

## Architecture

```
┌─────────────────────────────────┐
│   EmployeeController            │  ← REST API Layer
│   - Handles HTTP requests       │     Returns ResponseEntity
│   - Validates input             │     HTTP status codes
└────────────┬────────────────────┘
             │
             ▼
┌─────────────────────────────────┐
│   EmployeeService               │  ← Business Logic Layer
│   - Data validation             │     Generates UUIDs
│   - Business rules              │     Manages employees
└────────────┬────────────────────┘
             │
             ▼
┌─────────────────────────────────┐
│   HashMap (In-Memory)           │  ← Data Storage Layer
│   - Stores employee objects     │     Mock database
│   - Pre-loaded with 5 employees │
└─────────────────────────────────┘
```

## Testing

### Mock Data

The application comes pre-loaded with 5 employees:

| Name | Job Title | Salary |
|------|-----------|--------|
| John Doe | Software Engineer | $75,000 |
| Jane Smith | Senior Software Engineer | $95,000 |
| Michael Johnson | Engineering Manager | $120,000 |
| Emily Williams | Junior Developer | $68,000 |
| David Brown | DevOps Engineer | $85,000 |

### Run Tests

```bash
./gradlew test
```

### Code Formatting

Format code according to project standards:

```bash
./gradlew spotlessApply
```

Check code formatting:

```bash
./gradlew spotlessCheck
```

## Troubleshooting

### Issue: "permission denied: ./gradlew"

**Solution:**
```bash
chmod +x gradlew
```

### Issue: Port 8080 already in use

**Find the process:**
```bash
lsof -i :8080
```

**Kill the process:**
```bash
kill -9 <PID>
```

**Or change the port in `api/src/main/resources/application.yml`:**
```yaml
server:
  port: 8081
```

### Issue: Employee not found (404)

**Cause:** UUIDs are regenerated on each server restart.

**Solution:** Always fetch current UUIDs first:
```bash
curl http://localhost:8080/api/v1/employee
```

### Issue: Java version mismatch

**Check Java version:**
```bash
java -version
```

**Install Java 21:**
```bash
# macOS
brew install openjdk@21
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
```

### Issue: Build failed

**Clean and rebuild:**
```bash
./gradlew clean build
```

**Stop all Gradle daemons:**
```bash
./gradlew --stop
```

## Complete Workflow Example

```bash
# Terminal 1: Start the server
./gradlew bootRun

# Terminal 2: Test the API
# 1. View all employees
curl http://localhost:8080/api/v1/employee

# 2. Copy a UUID from the output above, then view that specific employee
curl http://localhost:8080/api/v1/employee/YOUR-UUID-HERE

# 3. Create a new employee
curl -X POST http://localhost:8080/api/v1/employee \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Sarah",
    "lastName": "Connor",
    "salary": 90000,
    "age": 32,
    "jobTitle": "Security Specialist",
    "email": "sarah.connor@company.com"
  }'

# 4. View all employees again (now includes Sarah)
curl http://localhost:8080/api/v1/employee

# 5. Get Sarah's UUID from the output, then view her details
curl http://localhost:8080/api/v1/employee/SARAH-UUID-HERE
```

## Browser Testing

You can test GET endpoints directly in your browser:

- **All employees:** http://localhost:8080/api/v1/employee
- **Single employee:** http://localhost:8080/api/v1/employee/{uuid}

## Using Postman

### GET All Employees
- Method: `GET`
- URL: `http://localhost:8080/api/v1/employee`

### GET Single Employee
- Method: `GET`
- URL: `http://localhost:8080/api/v1/employee/{uuid}`

### POST Create Employee
- Method: `POST`
- URL: `http://localhost:8080/api/v1/employee`
- Headers: `Content-Type: application/json`
- Body (raw JSON):
```json
{
  "firstName": "Bob",
  "lastName": "Smith",
  "salary": 80000,
  "age": 28,
  "jobTitle": "Frontend Developer",
  "email": "bob.smith@company.com"
}
```

## HTTP Status Codes

| Status Code | Description |
|-------------|-------------|
| `200 OK` | Request successful (GET) |
| `201 Created` | Employee created successfully (POST) |
| `400 Bad Request` | Validation error or malformed request |
| `404 Not Found` | Employee with given UUID not found |
| `500 Internal Server Error` | Server error |

## Design Patterns

- **Controller-Service Pattern**: Separation of concerns
- **DTO Pattern**: Clean API contracts
- **Dependency Injection**: Constructor-based injection
- **Repository Pattern**: Abstracted data access (via HashMap)

## Technologies Used

- **Spring Boot 3.2.10** - Application framework
- **Spring Web** - REST API support
- **Gradle 8.10.2** - Build tool
- **Java 21** - Programming language
- **Spotless** - Code formatter
- **Lombok** - Boilerplate reduction (optional)

## Key Features

### Input Validation
- Required field checks
- Age range validation (18-120)
- Salary validation (non-negative)
- Email format validation

### Error Handling
- Descriptive error messages
- Proper HTTP status codes
- Exception handling with ResponseStatusException

### Data Management
- In-memory storage (HashMap)
- Automatic UUID generation
- Automatic timestamp generation
- Pre-loaded mock data for testing

## Notes

- **Data Persistence:** This application uses in-memory storage. All data is reset when the server restarts.
- **UUIDs:** New UUIDs are generated each time the server starts. Always fetch current UUIDs before querying specific employees.
- **Production Ready:** For production use, replace the HashMap with a real database (PostgreSQL, MySQL, etc.) using Spring Data JPA.

## Future Enhancements

Potential improvements for production use:

- [ ] Add database integration (JPA/Hibernate)
- [ ] Implement UPDATE (PUT) endpoint
- [ ] Implement DELETE endpoint
- [ ] Add pagination for GET all employees
- [ ] Add search and filtering capabilities
- [ ] Implement Spring Security for authentication
- [ ] Add Swagger/OpenAPI documentation
- [ ] Add comprehensive unit and integration tests
- [ ] Add logging with SLF4J/Logback
- [ ] Add Docker support
- [ ] Add CI/CD pipeline

## Contributing

This is an educational project for ReliaQuest's Entry-Level Java Challenge.

## License

Educational use only.

## Contact

For questions or issues, please open an issue in the repository.

---

**Built with ❤️ using Spring Boot**
