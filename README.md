# Employee Management REST API - Complete Setup Guide

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.10-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-8.10.2-blue.svg)](https://gradle.org/)

A RESTful API for managing employee information, built with Spring Boot. This guide will walk you through **every step** from setup to running and testing the application.

---

## 📋 Table of Contents

- [Prerequisites](#prerequisites)
- [Initial Setup](#initial-setup)
- [Configuration Steps](#configuration-steps)
- [Building the Project](#building-the-project)
- [Running the Application](#running-the-application)
- [Testing the API](#testing-the-api)
- [API Documentation](#api-documentation)
- [Troubleshooting](#troubleshooting)
- [Project Structure](#project-structure)

---

## 🔧 Prerequisites

### Required Software

1. **Java 21 or Higher**
   
   Check if you have Java installed:
   ```bash
   java -version
   ```
   
   You should see:
   ```
   openjdk version "21" or higher
   ```
   
   **Don't have Java 21?** Install it:
   
   **macOS:**
   ```bash
   brew install openjdk@21
   sudo ln -sfn /opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-21.jdk
   ```
   
   **Windows:**
   Download from [Adoptium](https://adoptium.net/) or [Oracle](https://www.oracle.com/java/technologies/downloads/)
   
   **Linux:**
   ```bash
   sudo apt update
   sudo apt install openjdk-21-jdk
   ```

2. **Internet Connection** (for downloading dependencies)

3. **Terminal/Command Prompt**

---

## 🚀 Initial Setup

### Step 1: Clone or Download the Project

```bash
# If you have it as a zip, extract it first
# Then navigate to the project directory
cd entry-level-java-challenge
```

### Step 2: Make Gradle Wrapper Executable (Mac/Linux Only)

```bash
chmod +x gradlew
```

**Windows users:** Skip this step, use `gradlew.bat` instead of `./gradlew`

---

## ⚙️ Configuration Steps

### Step 1: Verify/Update Gradle Version

The project requires Gradle 8.10.2 or higher. Let's ensure it's set correctly:

```bash
# Check current Gradle version in the wrapper properties
cat gradle/wrapper/gradle-wrapper.properties
```

**If you see `gradle-7.6.4` or lower,** update it:

```bash
# Update to Gradle 8.10.2
sed -i '' 's/gradle-7.6.4-bin.zip/gradle-8.10.2-bin.zip/' gradle/wrapper/gradle-wrapper.properties
```

**Windows users:**
Open `gradle/wrapper/gradle-wrapper.properties` in a text editor and change:
```
distributionUrl=https\://services.gradle.org/distributions/gradle-8.10.2-bin.zip
```

### Step 2: Update Java Version in Build Configuration

```bash
# Update Java version to 21 in the build configuration
sed -i '' 's/JavaLanguageVersion.of(17)/JavaLanguageVersion.of(21)/' buildSrc/src/main/groovy/project-conventions.gradle
```

**Windows users:**
Open `buildSrc/src/main/groovy/project-conventions.gradle` and change:
```groovy
languageVersion = JavaLanguageVersion.of(21)
```

### Step 3: Clean All Cached Files

```bash
# Remove all buildSrc compiled files
find buildSrc -name "*.class" -type f -delete

# Remove build directories
rm -rf buildSrc/build
rm -rf .gradle
rm -rf api/build
```

**Windows users:**
```cmd
rmdir /s /q buildSrc\build
rmdir /s /q .gradle
rmdir /s /q api\build
```

### Step 4: Set Java 21 as Active (Important!)

```bash
# macOS/Linux: Set JAVA_HOME to Java 21
export JAVA_HOME=$(/usr/libexec/java_home -v 21)

# Verify it's set correctly
java -version
```

You should now see Java 21!

**Windows users:**
```cmd
# Find your Java 21 installation path, typically:
# C:\Program Files\Java\jdk-21
# Then set JAVA_HOME:
set JAVA_HOME=C:\Program Files\Java\jdk-21
set PATH=%JAVA_HOME%\bin;%PATH%
```

**To make this permanent (Mac/Linux):**
Add to your `~/.zshrc` or `~/.bashrc`:
```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
```

### Step 5: Stop Any Running Gradle Daemons

```bash
./gradlew --stop
```

This ensures a fresh start with the correct Java version.

---

## 🔨 Building the Project

### Step 1: Format the Code (Required)

The project uses Spotless for code formatting. **You must run this before building:**

```bash
./gradlew spotlessApply
```

This will automatically format all Java files according to the project's style guidelines.

### Step 2: Build the Project

```bash
./gradlew build
```

**Expected output:**
```
BUILD SUCCESSFUL in Xs
```

**If you see formatting violations:**
```bash
# Run spotless again
./gradlew spotlessApply

# Then build
./gradlew build
```

**If build fails with Java version errors:**
- Make sure you completed Step 4 in Configuration (setting JAVA_HOME)
- Run `java -version` to verify you're using Java 21
- Try `./gradlew --stop` then `./gradlew build` again

---

## 🎯 Running the Application

### Step 1: Start the Server

```bash
./gradlew bootRun
```

### Step 2: Wait for Server to Start

You'll see output like this:
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::               (v3.2.10)

Tomcat initialized with port 8080 (http)
Tomcat started on port 8080 (http) with context path ''
Started EntryLevelJavaChallengeApplication in X.XXX seconds
```

**Important:** The terminal will show "IDLE" - this is normal! The server is running and waiting for requests.

### Step 3: Keep This Terminal Open

**Do NOT close this terminal!** The server needs to stay running.

---

## 🧪 Testing the API

### Open a NEW Terminal Window

Keep the server running in the first terminal, and open a second terminal for testing.

### Test 1: View All Employees

**Using Terminal:**
```bash
curl http://localhost:8080/api/v1/employee
```

**Using Browser:**
Open this URL:
```
http://localhost:8080/api/v1/employee
```

**Expected Response:**
You'll see 5 pre-loaded employees in JSON format:
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
  },
  ... (4 more employees)
]
```

### Test 2: View a Single Employee

**Step 2a:** From the response above, **copy any UUID**. For example:
```
58852996-9c72-4406-8286-f9b77b290e9f
```

**Step 2b:** Use that UUID in the request:

**Using Terminal:**
```bash
# Replace the UUID with one you copied
curl http://localhost:8080/api/v1/employee/58852996-9c72-4406-8286-f9b77b290e9f
```

**Using Browser:**
```
http://localhost:8080/api/v1/employee/58852996-9c72-4406-8286-f9b77b290e9f
```

**Expected Response:**
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

### Test 3: Create a New Employee

**Using Terminal:**
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

**Expected Response (201 Created):**
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

### Test 4: Verify the New Employee Was Added

```bash
curl http://localhost:8080/api/v1/employee
```

You should now see **6 employees** including Alice!

### Stopping the Server

In the terminal where the server is running, press:
```
Ctrl + C
```

---

## 📚 API Documentation

### Base URL

```
http://localhost:8080/api/v1/employee
```

### Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/v1/employee` | Get all employees |
| `GET` | `/api/v1/employee/{uuid}` | Get single employee by UUID |
| `POST` | `/api/v1/employee` | Create new employee |

### Request/Response Examples

#### 1. GET All Employees

**Request:**
```bash
GET /api/v1/employee
```

**Response: 200 OK**
```json
[
  {
    "uuid": "string",
    "firstName": "string",
    "lastName": "string",
    "fullName": "string",
    "salary": 0,
    "age": 0,
    "jobTitle": "string",
    "email": "string",
    "contractHireDate": "2025-10-23T15:12:36.929351Z",
    "contractTerminationDate": null
  }
]
```

#### 2. GET Employee by UUID

**Request:**
```bash
GET /api/v1/employee/{uuid}
```

**Response: 200 OK**
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

**Response: 404 Not Found**
```json
{
  "timestamp": "2025-10-23T15:16:32.625+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Employee not found with UUID: {uuid}"
}
```

#### 3. POST Create Employee

**Request:**
```bash
POST /api/v1/employee
Content-Type: application/json
```

**Request Body:**
```json
{
  "firstName": "Alice",
  "lastName": "Johnson",
  "salary": 85000,
  "age": 29,
  "jobTitle": "Data Scientist",
  "email": "alice.johnson@company.com"
}
```

**Response: 201 Created**
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

**Response: 400 Bad Request**
```json
{
  "timestamp": "2025-10-23T15:16:32.625+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "First name is required"
}
```

### Field Requirements

| Field | Type | Required | Validation |
|-------|------|----------|------------|
| `firstName` | String | ✅ Yes | Cannot be empty |
| `lastName` | String | ✅ Yes | Cannot be empty |
| `email` | String | ✅ Yes | Cannot be empty |
| `salary` | Integer | ✅ Yes | Must be ≥ 0 |
| `age` | Integer | ✅ Yes | Must be between 18-120 |
| `jobTitle` | String | ❌ No | Optional |

### Auto-Generated Fields

- `uuid` - Automatically generated on creation
- `fullName` - Created from firstName + lastName
- `contractHireDate` - Set to current timestamp
- `contractTerminationDate` - null by default (for active employees)

### HTTP Status Codes

| Status | Description |
|--------|-------------|
| `200 OK` | Request successful (GET) |
| `201 Created` | Employee created successfully (POST) |
| `400 Bad Request` | Validation error or invalid input |
| `404 Not Found` | Employee with given UUID doesn't exist |
| `500 Internal Server Error` | Server error |

---

## 🛠️ Troubleshooting

### Issue 1: "permission denied: ./gradlew"

**Solution:**
```bash
chmod +x gradlew
```

### Issue 2: Java Version Error

**Error Message:**
```
Unsupported class file major version XX
```

**Solution:**
```bash
# Make sure you're using Java 21
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
java -version

# Stop Gradle daemons
./gradlew --stop

# Try again
./gradlew build
```

### Issue 3: Build Failed with Formatting Violations

**Error Message:**
```
Task :api:spotlessJavaCheck FAILED
The following files had format violations
```

**Solution:**
```bash
# Apply formatting
./gradlew spotlessApply

# Build again
./gradlew build
```

### Issue 4: Port 8080 Already in Use

**Error Message:**
```
Port 8080 is already in use
```

**Solution 1 - Find and Kill the Process:**
```bash
# Find what's using port 8080
lsof -i :8080

# Kill the process (replace PID with actual process ID)
kill -9 PID
```

**Solution 2 - Change the Port:**
Edit `api/src/main/resources/application.yml`:
```yaml
server:
  port: 8081
```

### Issue 5: "Employee not found" (404 Error)

**Cause:** UUIDs are randomly generated when the server starts. They change each time you restart.

**Solution:**
1. Get fresh UUIDs first:
   ```bash
   curl http://localhost:8080/api/v1/employee
   ```
2. Copy a UUID from the response
3. Use that UUID in your request

### Issue 6: Build Succeeds but Server Won't Start

**Check if the build actually completed:**
```bash
./gradlew clean build
```

**Make sure no other instance is running:**
```bash
./gradlew --stop
ps aux | grep java
```

**Start with verbose logging:**
```bash
./gradlew bootRun --info
```

### Issue 7: Gradle Daemon Issues

**Solution:**
```bash
# Stop all daemons
./gradlew --stop

# Check status
./gradlew --status

# Clean everything
rm -rf ~/.gradle/caches/
rm -rf .gradle/

# Try again
./gradlew build
```

### Issue 8: Windows-Specific Issues

**Use `gradlew.bat` instead of `./gradlew`:**
```cmd
gradlew.bat build
gradlew.bat bootRun
```

**If you get path errors:**
- Make sure JAVA_HOME is set correctly
- Use full paths if needed
- Run Command Prompt as Administrator

---

## 📁 Project Structure

```
entry-level-java-challenge/
├── api/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/challenge/api/
│   │   │   │   ├── controller/
│   │   │   │   │   └── EmployeeController.java      # REST endpoints
│   │   │   │   ├── service/
│   │   │   │   │   └── EmployeeService.java         # Business logic
│   │   │   │   ├── model/
│   │   │   │   │   ├── Employee.java                # Interface
│   │   │   │   │   └── EmployeeImpl.java            # Implementation
│   │   │   │   ├── dto/
│   │   │   │   │   └── CreateEmployeeRequest.java   # Request DTO
│   │   │   │   └── EntryLevelJavaChallengeApplication.java
│   │   │   └── resources/
│   │   │       └── application.yml                   # Configuration
│   │   └── test/
│   └── build.gradle
├── buildSrc/
│   └── src/main/groovy/
│       └── project-conventions.gradle                # Build configuration
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties                 # Gradle version
├── gradlew                                          # Gradle wrapper (Unix)
├── gradlew.bat                                      # Gradle wrapper (Windows)
├── settings.gradle
└── README.md
```

---

## 🏗️ Architecture

```
┌─────────────────────────────────┐
│   EmployeeController            │  ← REST API Layer
│   @RestController                │     - Handles HTTP requests
│   @RequestMapping               │     - Returns ResponseEntity
└────────────┬────────────────────┘     - Validates input
             │
             ▼
┌─────────────────────────────────┐
│   EmployeeService               │  ← Business Logic Layer
│   @Service                      │     - Data validation
└────────────┬────────────────────┘     - UUID generation
             │                           - Business rules
             ▼
┌─────────────────────────────────┐
│   HashMap (In-Memory Storage)   │  ← Data Storage Layer
│   Map<UUID, Employee>           │     - Mock database
└─────────────────────────────────┘     - Pre-loaded data
```

---

## 📝 Complete Workflow Example

Here's a complete example from start to finish:

```bash
# ===== TERMINAL 1: Setup and Start Server =====

# 1. Navigate to project
cd entry-level-java-challenge

# 2. Set Java 21
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
java -version

# 3. Clean and setup
./gradlew --stop
rm -rf .gradle buildSrc/build

# 4. Format and build
./gradlew spotlessApply
./gradlew build

# 5. Start server
./gradlew bootRun

# Wait for: "Tomcat started on port 8080"


# ===== TERMINAL 2: Test the API =====

# 1. View all employees
curl http://localhost:8080/api/v1/employee

# 2. Copy a UUID from the output above, then view that employee
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

# 5. Get Sarah's UUID from step 4, then view her details
curl http://localhost:8080/api/v1/employee/SARAH-UUID-HERE


# ===== TERMINAL 1: Stop Server =====
# Press Ctrl + C
```

---

## 🎓 Mock Data

The application comes pre-loaded with 5 employees for testing:

| Name | Job Title | Salary | Age |
|------|-----------|--------|-----|
| John Doe | Software Engineer | $75,000 | 30 |
| Jane Smith | Senior Software Engineer | $95,000 | 35 |
| Michael Johnson | Engineering Manager | $120,000 | 42 |
| Emily Williams | Junior Developer | $68,000 | 28 |
| David Brown | DevOps Engineer | $85,000 | 33 |

**Note:** UUIDs are randomly generated each time the server starts.

---

## ✅ Pre-Deployment Checklist

Before submitting or deploying, verify:

- [ ] Java 21 is installed and active
- [ ] Gradle wrapper properties updated to 8.10.2
- [ ] Java version in build config set to 21
- [ ] Code formatted with Spotless: `./gradlew spotlessApply`
- [ ] Build passes: `./gradlew build`
- [ ] Server starts successfully: `./gradlew bootRun`
- [ ] Can view all employees via browser/curl
- [ ] Can view single employee by UUID
- [ ] Can create new employee via POST
- [ ] All endpoints return expected responses

---

## 🔑 Key Features

- ✅ RESTful API design with proper HTTP methods
- ✅ Spring Boot 3.2.10 framework
- ✅ In-memory data storage (HashMap)
- ✅ Automatic UUID generation
- ✅ Comprehensive input validation
- ✅ Clean architecture (Controller → Service → Data)
- ✅ Descriptive error messages
- ✅ Code formatting with Spotless
- ✅ Pre-loaded mock data for testing

---

## 📞 Additional Help

### Common Commands Reference

```bash
# Format code
./gradlew spotlessApply

# Build project
./gradlew build

# Clean build
./gradlew clean build

# Run application
./gradlew bootRun

# Stop Gradle daemons
./gradlew --stop

# Check Gradle status
./gradlew --status

# Run with verbose output
./gradlew build --info
```

### Using Postman Instead of curl

1. **GET All Employees**
   - Method: GET
   - URL: `http://localhost:8080/api/v1/employee`

2. **GET Single Employee**
   - Method: GET
   - URL: `http://localhost:8080/api/v1/employee/{uuid}`

3. **POST Create Employee**
   - Method: POST
   - URL: `http://localhost:8080/api/v1/employee`
   - Headers: `Content-Type: application/json`
   - Body (raw JSON):
   ```json
   {
     "firstName": "Test",
     "lastName": "User",
     "salary": 80000,
     "age": 25,
     "jobTitle": "Developer",
     "email": "test@example.com"
   }
   ```

---

## 🚨 Important Notes

1. **Data Persistence:** This application uses in-memory storage. All data resets when you restart the server.

2. **UUIDs Change:** Employee UUIDs are regenerated on each server restart. Always fetch current UUIDs before querying specific employees.

3. **Java Version:** The project REQUIRES Java 21. It will not work with older versions.

4. **Gradle Version:** Must use Gradle 8.10.2 or higher for Java 21 support.

5. **Code Formatting:** Always run `./gradlew spotlessApply` before building to avoid formatting violations.

6. **Port 8080:** Make sure port 8080 is available. Change in `application.yml` if needed.

---

## 📄 License

This is an educational project for ReliaQuest's Entry-Level Java Challenge.

---

## 🎉 Success!

If you've followed all the steps and can:
- ✅ Start the server without errors
- ✅ View all employees in your browser
- ✅ Get a single employee by UUID
- ✅ Create new employees via POST request

**Congratulations! Your Employee Management API is working perfectly!** 🚀

---

**Built with ❤️ using Spring Boot**

For questions or issues, refer to the [Troubleshooting](#troubleshooting) section above.
