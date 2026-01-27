CVR Lookup Application - Backend

* A Spring Boot backend service that provides a REST API for looking up publicly available information about Danish companies using their CVR number.
* The backend acts as an intermediary between the frontend application and the external CVR data provider, ensuring secure API access and clean separation of concerns.

Features
* REST API for CVR company lookup
* Integration with the external Danish CVR data provider (virkdata.dk)
* Secure handling of API credentials (not exposed to frontend)
* Database-based caching to minimize external API calls
* Clean layered architecture
* Designed to be used by a separate frontend application (e.g., JavaFX)

Architecture
* The backend is built using a layered architecture:

Controller layer
* Exposes REST endpoints for CVR lookup

Business Logic Layer (BLL)
* Determines whether to return cached data or call the api, and only returns cached data if it's less than 24 hours old
  
Data Access Layer (DAL)
* Handles communication with the external CVR API + Database cache

Business Entities (BE)
* Domain models representing Danish companies
The backend decouples the frontend from the external CVR API, allowing the system to be extended with caching(In progress), authentication, or additional data sources in the future.

Prerequisites

* Java 11 or higher
* Maven
* Valid API key for the CVR data provider

API Configuration
* The backend uses one configuration source: config/API.key - See the example file in the config-package

Database cache behavior
* The backend implements a 24-hour cache:
1. When a request is received, the backend first checks the database  
2. If the CVR number exists **and is less than 24 hours old**, data is returned from the database  
3. If the CVR number is **missing or older than 24 hours**:
   - Data is fetched from the external CVR API  
   - The database is updated  
   - The fresh data is returned to the client  

* This reduces external API usage and improves performance.

Running the Application
* Create a config folder in the project root
* Add an API.key file containing your CVR API key
* Run the Spring Boot application
* The backend will be available on http://localhost:8080

API Endpoint
* /api/company/{cvr}
* Returns company information for the given Danish CVR number.

For testing purposes, you can use these real Danish companies:
* 10150817 - Erhvervsstyrelsen
* 36213728 - Ørsted A/S
* 54562519 - LEGO A/S

Project Structure

* controller   – REST endpoints
* bll          – Business Logic + cache handling
* dal          – External API communication + database access
* be           – Business entities
* config       – External configuration (not tracked in Git)

If you encounter any issues or have questions:
* Open an issue on GitHub
* Check existing issues for solutions
* Review the API documentation

Link to Frontend repo: https://github.com/JakobPrimdal/CVRLookUpApp-Frontend
