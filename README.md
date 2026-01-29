🔐 Digital Services API












RESTful API for authentication, authorization, and user management using JWT and role-based access control.

## 📋 Table of Contents

- [Overview](#-overview)
- [Technologies](#-technologies--stack)
- [Architecture](#-architecture)
- [Security Model](#-security-model)
- [API Endpoints](#-api-endpoints)
- [Validation & Error Handling](#-validation--error-handling)
- [API Documentation](#-api-documentation)
- [How to Run](#-how-to-run)
- [Testing](#-testing)
- [Future Improvements](#-future-improvements)
- [Author](#-author)
- [License](#-license)

🚀 Overview

Digital Services API is a secure RESTful backend built with Java 17 and Spring Boot, focused on:

🔐 JWT-based authentication

🧑‍💻 Role-based authorization (USER / ADMIN)

👥 User management

✅ Input validation and global error handling

📄 OpenAPI / Swagger documentation

🧪 Unit and integration testing

The project was designed with clean structure, testability, and real-world backend best practices in mind, making it suitable for production scenarios and portfolio demonstration.

Main Goals

✅ Secure authentication with JWT
✅ Role-based access control (RBAC)
✅ Clean layered architecture
✅ Centralized exception handling
✅ API documentation with Swagger
✅ Automated tests (unit + integration)
✅ Simple in-memory database for development

## 🛠️ Technologies & Stack

| Technology | Purpose |
|-----------|---------|
| Java 17 | Programming language |
| Spring Boot 3.5.10 | Application framework |
| Spring Web MVC | RESTful API |
| Spring Security | Authentication & authorization |
| JWT (jjwt) | Token-based security |
| Spring Data JPA | Persistence layer |
| H2 Database | In-memory database (dev/test) |
| Bean Validation | Input validation |
| OpenAPI 3.1 | API specification |
| Swagger UI | Interactive API docs |
| JUnit 5 | Testing framework |
| Mockito | Unit test mocking |
| MockMvc | Integration testing |
| Maven | Build & dependency management |
| Git | Version control |

## 📁 Architecture

The project follows a layered architecture, separating concerns clearly:

```
┌──────────────┐
│ Controller   │  → HTTP layer (REST endpoints)
└──────┬───────┘
       ▼
┌──────────────┐
│ Service      │  → Business logic & security rules
└──────┬───────┘
       ▼
┌──────────────┐
│ Repository   │  → Persistence (JPA / H2)
└──────────────┘
```

### Responsibilities

| Layer | Responsibility |
|-------|----------------|
| Controller | Handles requests & responses |
| Service | Business logic, authentication, authorization |
| Repository | Database access |
| Security | JWT, filters, roles, access control |
| DTOs | API contracts (request / response) |

### Benefits

✅ High cohesion  
✅ Low coupling  
✅ Easy testing  
✅ Scalable structure

## 🔐 Security Model

The API uses stateless JWT authentication.

### Authentication Flow
```
Client → /auth/login → JWT Token
Client → Authorization: Bearer <token>
Spring Security → Role & permission validation
```

### Roles

| Role | Permissions |
|------|-------------|
| ROLE_USER | Access own data |
| ROLE_ADMIN | Manage users, list all users |

### JWT Claims

```json
{
  "sub": "user@email.com",
  "role": "ROLE_ADMIN",
  "iat": 1700000000,
  "exp": 1700003600
}
```

🔗 API Endpoints
Authentication
Login
POST /auth/login


Request

{
  "email": "user@email.com",
  "password": "123456"
}


Response

{
  "token": "jwt-token-here"
}

Get Current User
GET /auth/me


Response

{
  "email": "user@email.com",
  "role": "ROLE_USER"
}

Users (ADMIN only)
List Users
GET /users

Create User
POST /users

Status Codes
Status	Description
200 OK	Successful request
201 Created	Resource created
400 Bad Request	Validation error
401 Unauthorized	Missing / invalid token
403 Forbidden	Insufficient role
500 Internal Server Error	Unexpected error

The API uses Bean Validation and a GlobalExceptionHandler.

### Validation Example
```java
@NotBlank
@Email
private String email;
```

### Error Response Example
```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "email": "must be a well-formed email address"
  }
}
```

**Handled exceptions include:**
- Validation errors
- Authentication failures
- Access denied
- Illegal arguments
- Unexpected server errors

## 📚 API Documentation

The API is documented with OpenAPI 3.1 and Swagger UI.

After running the project, access:

🔗 **http://localhost:8080/swagger-ui.html**

### Features
- 📖 Endpoint documentation
- 🧪 Interactive testing
- 🔐 JWT authorization via Swagger

## ▶️ How to Run

### Prerequisites
- ☕ Java 17+
- 📦 Maven

### Steps

```bash
# Clone the repository
git clone https://github.com/AlexandreKanha/digital-services-platform.git

# Enter project directory
cd digital-services-platform/backend/digitalservices

# Run application
mvn spring-boot:run
```

Application will be available at:

🔗 **http://localhost:8080**

## 🧪 Testing

The project includes unit and integration tests.

### Test Strategy

| Type | Description |
|------|-------------|
| ✅ Unit Tests | Service layer with Mockito |
| ✅ Integration Tests | Controllers with MockMvc |
| ✅ Security Tests | JWT + role validation |
| ✅ Exception Tests | Validation and access errors |

### Run Tests
```bash
mvn test
```

### Example Scenarios Covered
- ✓ Login with valid credentials returns JWT
- ✓ Login with invalid password throws 401
- ✓ Access protected endpoint without token returns 401
- ✓ USER role cannot access ADMIN endpoint
- ✓ ADMIN role can list users
- ✓ Invalid email format returns validation error
- ✓ Email already registered returns error

## 🔮 Future Improvements

| Feature | Description |
|---------|-------------|
| 🐳 Docker | Containerization |
| 🔄 Refresh Token | Token renewal strategy |
| 🔐 OAuth2 | External authentication providers |
| 🧱 Hexagonal Architecture | Further decoupling |
| 📊 Metrics | Micrometer + Prometheus |
| 🗄️ PostgreSQL | Replace H2 for production |
| 🚀 CI/CD | GitHub Actions pipeline |
| 📧 Email | Email verification & notifications |
## 👨‍💻 Author

**Alexandre Kanha**  
Backend Developer | Java | Spring Boot | APIs | Security

## 📄 License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.

---

<div align="center">

⭐ If you found this project useful, consider giving it a star! ⭐

</div>
