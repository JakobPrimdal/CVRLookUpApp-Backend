# CVR Lookup Application – Backend

A **Spring Boot REST API** that allows users to look up publicly available information about Danish companies using their **CVR number**.

The backend acts as an intermediary between a frontend application and the official Danish CVR data provider.
It securely handles API credentials and implements a **database cache** to reduce external API calls and improve performance.

This project is part of a full-stack application where a separate frontend consumes the API.

---

## Features

* REST API for CVR company lookup
* Integration with the Danish CVR data provider (virkdata.dk)
* Secure handling of API credentials using environment variables
* PostgreSQL database caching to minimize external API calls
* 24-hour cache expiration strategy
* Structured API responses using Data Transfer Objects (DTOs)
* Centralized error handling
* Clean layered architecture
* Designed to be consumed by a separate frontend application

---

## Tech Stack

**Backend**

* Java
* Spring Boot
* Maven
* PostgreSQL

**Deployment**

* Hosted on Render

---

## Architecture

The backend follows a layered architecture.

### Controller Layer

Exposes REST endpoints used by the frontend application.

### Business Logic Layer (BLL)

Handles application logic, including cache validation and determining whether data should be fetched from the database or the external API.

### Data Access Layer (DAL)

Responsible for:

* Communication with the external CVR API
* Database access and caching

### Business Entities (BE)

Domain models representing Danish companies and their associated data.

---

## Database Cache Strategy

The backend implements a **24-hour caching mechanism**.

When a request is received:

1. The database is checked for the requested CVR number.
2. If cached data exists and is **less than 24 hours old**, it is returned immediately.
3. If the data is **missing or expired**:

   * The backend fetches fresh data from the external CVR API
   * The database cache is updated
   * The fresh data is returned to the client.

This approach:

* Reduces external API usage
* Improves response times
* Provides more stable API behaviour.

---

## Environment Configuration

Sensitive configuration such as **API keys and database credentials** are not stored in the repository.

Instead, they are provided as **environment variables** and injected into the application through `application.properties`.

Example configuration:

```properties
cvr.api.key=${CVR_API_KEY}
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

When deployed on Render, these variables are configured directly in the platform's environment settings.

---

## API Endpoint

```
GET /api/company/{cvr}
```

Returns company information for the given Danish CVR number.

Example test companies:

```
54562519 – LEGO A/S
36213728 – Ørsted A/S
10150817 – Erhvervsstyrelsen
```

---

## Data Transfer Objects (DTO)

The API uses **DTOs (Data Transfer Objects)** to control what data is returned to the client.

DTOs help ensure:

* Internal domain models are not exposed directly
* API responses remain stable even if internal models change
* Only relevant data is returned to the frontend

Current DTOs include:

`CompanyResponseDTO`
Contains the company data returned when a valid CVR number is requested.

`ErrorResponseDTO`
Standardized error response returned when an error occurs.

Example error response:

```json
{
  "error": "Company not found",
  "message": "No company found for the provided CVR number",
  "status": 404
}
```

## Error Handling

The backend implements structured error handling using custom exceptions and a global exception handler.

### Custom Exceptions

`CompanyNotFoundException`
Thrown when a requested CVR number does not exist.

`CompanyServiceException`
Thrown when an unexpected error occurs while communicating with the external CVR API or internal services.

### Global Exception Handler

A centralized exception handler ensures that errors are converted into **consistent API responses**.

This provides:

* Clean error messages for the frontend
* Consistent API behaviour

---

## Running the Application Locally

1. Clone the repository

2. Set the required environment variables:

```
CVR_API_KEY
DB_URL
DB_USERNAME
DB_PASSWORD
```

3. Run the Spring Boot application.

The backend will start on:

```
http://localhost:8080
```

---

## Project Structure

```
controller  – REST API endpoints
bll         – Business logic and cache validation
dal         – External API communication and database access
dto         – Data Transfer Objects used for API responses
exception   – Custom exceptions and global error handling
be          – Business entities (domain models)
config      – Application configuration
```

---

## Frontend

This backend is designed to work with a separate frontend application.

Frontend repository:

https://github.com/JakobPrimdal/CVRLookUpApp-Frontend

---

## Author

Jakob Primdal
Datamatiker/Computer Science student
