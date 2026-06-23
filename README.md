# Central Auth Service

`central-auth-service` is a backend authentication and authorization service built on **Spring Boot 3.x / 4.x**. It provides a centralized solution for user authentication (using **JWT - JSON Web Tokens**) and authorization (using **RBAC - Role-Based Access Control**).

This service is designed to be easily integrated as an Identity Provider (IDP) or Authorization Server for microservices architectures or web applications.

---

## 🚀 Key Features

- **User Authentication:**
  - Login and token issuance (Access Token & Refresh Token) via `/auth/token`.
  - Token refreshing via `/auth/refresh`.
  - Token introspection/validation via `/auth/introspect`.
  - Logout and token invalidation via `/auth/logout`.
- **Role-Based Access Control (RBAC):**
  - Manage Permissions (functional capabilities).
  - Manage Roles (collections of permissions).
  - Method-level security (`@PreAuthorize`) and endpoint-level security configurations with Spring Security.
- **User Management:**
  - CRUD operations (Create, Read, Update, Delete) on user profiles.
  - Retrieve current logged-in user information (`/users/myInfo`).
  - Secure password hashing using **BCrypt**.
- **Data Auto-initialization:**
  - Automatically seeds default roles (`ADMIN`, `USER`) and the initial administrator account (`admin`/`admin`) if the database is empty upon startup.
- **API Documentation (Swagger UI):**
  - Interactive API documentation to easily test and explore endpoints.

---

## 🛠️ Technology Stack & Libraries

- **Language:** Java 17
- **Framework:** Spring Boot 4.0.5
- **Security:** Spring Security, Spring Boot Starter OAuth2 Resource Server
- **JWT Handling:** Nimbus JOSE + JWT
- **Database Access:** Spring Data JPA, Hibernate
- **Database:** MySQL (Production/Staging), H2 Database (Testing)
- **Caching & Session:** Spring Data Redis (for token/logout blacklisting)
- **Object Mapping:** MapStruct
- **Utilities:** Project Lombok
- **API Documentation:** Springdoc OpenAPI (Swagger UI)

---

## 📁 Source Code Directory Structure

```text
src/main/java/com/nhanthanhle/centralauthservice/
├── CentralAuthServiceApplication.java   # Main application entry point
├── config/                             # Spring Security, JWT, and Data Seeding configs
├── constant/                           # System-wide constants
├── controller/                         # REST Controllers exposing the endpoints
├── dto/                                # Data Transfer Objects (Request / Response)
├── entity/                             # JPA Entities (User, Role, Permission)
├── enums/                              # Common Enums
├── exception/                          # Global Exception Handler and custom exceptions
├── mapper/                             # MapStruct interfaces for Entity <-> DTO mapping
├── repository/                         # Spring Data JPA repositories for DB operations
├── service/                            # Business logic services
└── validator/                          # Custom validation annotations (e.g., DobConstraint)
```

---

## ⚙️ Configuration

The main configuration file is located at: [application.yaml](file:///c:/Users/nhan/workplace/swlearn/javalearn/central-auth-service/src/main/resources/application.yaml)

```yaml
server:
  port: 7777                          # Server port
  servlet:
    context-path: /identity           # Global context path prefix for all APIs

spring:
  datasource:
    url: jdbc:mysql://localhost:3307/central_auth_service  # MySQL Connection URL
    username: root
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update                # Auto-updates database schema to match entities
    show-sql: true                    # Prints executed SQL queries in console

jwt:
  signerKey: "60de726f31ded068a7a1d7fe75e76c1aed2c91ee339eed322e5bfe3f9e9a0a66" # Secret key for signing JWTs
  valid-duration: 360                 # Access Token lifetime (seconds)
  refreshable-duration: 360000        # Refresh Token lifetime (seconds)
```

> [!IMPORTANT]
> - Ensure **MySQL** is installed, running on port `3307`, and that the `central_auth_service` database exists before starting the application.
> - Ensure **Redis** is running, as it is used to manage token blacklisting and logouts.

---

## 🏃 Running the Application

### 1. Prerequisites
- **Java 17** or higher installed (`java -version`).
- A running MySQL Server with the schema created:
  ```sql
  CREATE DATABASE central_auth_service;
  ```

### 2. Build & Run
Run the following commands in the project root directory:

- **On Windows:**
  ```bash
  mvnw.cmd clean spring-boot:run
  ```
- **On Linux/macOS:**
  ```bash
  ./mvnw clean spring-boot:run
  ```

Once successfully started, the application will be listening on port `7777` with the base URL `http://localhost:7777/identity`.

### 🔑 Default Credentials
During the first run, the system automatically initializes a default administrator:
- **Username:** `admin`
- **Password:** `admin`
- **Role:** `ADMIN`

> [!WARNING]
> Please change the default password of the `admin` account immediately after logging in for security purposes.

---

## 📋 API Endpoints Reference

All APIs are prefixed with `/identity`.

### 1. Authentication APIs (`/identity/auth`)

| Method | Endpoint | Requires Authentication | Description |
| :--- | :--- | :---: | :--- |
| **POST** | `/auth/token` | No | Login to obtain Access Token (JWT) and Refresh Token |
| **POST** | `/auth/refresh` | No | Obtain a new Access Token using a Refresh Token |
| **POST** | `/auth/introspect` | No | Verify if a JWT is valid and not expired |
| **POST** | `/auth/logout` | No | Log out and blacklist the token |

### 2. User Management APIs (`/identity/users`)

| Method | Endpoint | Requires Authentication | Allowed Roles | Description |
| :--- | :--- | :---: | :---: | :--- |
| **POST** | `/users` | No | Any | Register a new user account |
| **GET** | `/users/myInfo` | Yes | Any | Get profile details of the currently logged-in user |
| **GET** | `/users` | Yes | `ADMIN` | List all users in the system |
| **GET** | `/users/{userId}` | Yes | `ADMIN` | Get profile details of a user by ID |
| **PUT** | `/users/{userId}` | Yes | Owner / `ADMIN` | Update user details |
| **DELETE** | `/users/{userId}` | Yes | `ADMIN` | Delete a user account |

### 3. Roles & Permissions APIs (`/identity/roles` & `/identity/permissions`)

| Method | Endpoint | Requires Authentication | Allowed Roles | Description |
| :--- | :--- | :---: | :---: | :--- |
| **POST** | `/roles` | Yes | `ADMIN` | Create a new role with a list of permissions |
| **GET** | `/roles` | Yes | `ADMIN` | Retrieve all roles |
| **DELETE** | `/roles/{roleName}` | Yes | `ADMIN` | Delete a role |
| **POST** | `/permissions` | Yes | `ADMIN` | Create a new permission |
| **GET** | `/permissions` | Yes | `ADMIN` | Retrieve all permissions |
| **DELETE** | `/permissions/{id}` | Yes | `ADMIN` | Delete a permission |

---

## 📖 API Documentation & Swagger UI

Once the application is running, you can access the Swagger UI interface to explore and test the REST APIs:

- **Swagger UI:** `http://localhost:7777/identity/swagger-ui.html`
- **OpenAPI JSON Docs:** `http://localhost:7777/identity/v1/api-docs`

---

## 🗄️ Database Schema (DDL)

The database schema definitions for the roles and permissions relationships from [ddl_Role.sql](file:///c:/Users/nhan/workplace/swlearn/javalearn/central-auth-service/ddl_Role.sql):

```sql
CREATE TABLE `role`
(
    name          VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NULL,
    CONSTRAINT pk_role PRIMARY KEY (name)
);

CREATE TABLE role_permissions
(
    role_name        VARCHAR(255) NOT NULL,
    permissions_name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_role_permissions PRIMARY KEY (role_name, permissions_name)
);

ALTER TABLE role_permissions
    ADD CONSTRAINT fk_rolper_on_permission FOREIGN KEY (permissions_name) REFERENCES permission (name);

ALTER TABLE role_permissions
    ADD CONSTRAINT fk_rolper_on_role FOREIGN KEY (role_name) REFERENCES `role` (name);
```
