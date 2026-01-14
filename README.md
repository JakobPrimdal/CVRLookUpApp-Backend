CVR Lookup Application - 

* A Spring Boot backend service that provides a REST API for looking up publicly available information about Danish companies using their CVR number.
* The backend acts as an intermediary between the frontend application and the external CVR data provider, ensuring secure API access and clean separation of concerns.

Features
* REST API for CVR company lookup
* Integration with external Danish CVR data provider
* Secure handling of API credentials (not exposed to frontend)
* Clean layered architecture
* Designed to be used by a separate frontend application (e.g. JavaFX)

Architecture
* The backend is built using a layered architecture:

Controller layer
* Exposes REST endpoints for CVR lookup

  
Data Access Layer (DAL)
* Handles communication with the external CVR API


Business Entities (BE)
* Domain models representing Danish companies
The backend decouples the frontend from the external CVR API, allowing the system to be extended with caching(In progress), authentication, or additional data sources in the future.

Prerequisites

* Java 11 or higher
* Maven
* Valid API key for the CVR data provider


API Configuration
* The backend uses one configuration source: config/API.key - See the example file in the config-package

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
* dal          – External API communication
* bll          – Business Logic
* be           – Business entities
* config       – External configuration (not tracked in Git)

If you encounter any issues or have questions:
* Open an issue on GitHub
* Check existing issues for solutions
* Review the API documentation
