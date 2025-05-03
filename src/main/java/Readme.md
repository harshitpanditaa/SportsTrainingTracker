# Sports Training Performance Tracker

## Overview
The Sports Training Performance Tracker is a comprehensive Spring Boot application designed to help coaches and analysts track and analyze athlete performance data. The system provides secure REST APIs for managing athletes and their performance records, with role-based access control to ensure data security.

## Technologies Used
- **Java 21**
- **Spring Boot 3.4.5**
- **Spring Data JPA** - For database interaction
- **Spring Security** - For authentication and authorization
- **JWT (JSON Web Token)** - For secure authentication
- **Spring AOP** - For aspect-oriented programming and performance logging
- **MySQL** - Primary database
- **H2 Database** - For testing
- **Lombok** - For reducing boilerplate code
- **SpringDoc OpenAPI** - For API documentation
- **Maven** - For dependency management and build

## Data Model

### Athlete
- **id** (Long): Unique identifier
- **name** (String): Full name of the athlete
- **age** (int): Age of the athlete
- **team** (String): Team the athlete belongs to
- **Relation**: One athlete → many performance records

### PerformanceRecord
- **id** (Long): Unique record ID
- **athlete** (Athlete): Linked to a specific athlete
- **metric** (String): Type of measurement (e.g., "speed", "stamina", "lap time")
- **value** (Double): The recorded value for the metric
- **remarks** (String): Additional notes about the performance
- **date** (Date): When the performance was recorded

## Security Implementation
The application implements a robust security system using Spring Security and JWT:

- **Authentication**: JWT-based authentication with username/password
- **Authorization**: Role-based access control with two roles:
  - **COACH**: Can create, read, update, and delete athletes and performance records
  - **ANALYST**: Can only read athlete and performance record data
- **Endpoints Security**: 
  - Public endpoints: `/auth/**` (for registration and login)
  - Protected endpoints: All `/api/**` endpoints require authentication

## API Endpoints

### Authentication
- **POST /auth/register** - Register a new user (username, password, role)
- **POST /auth/login** - Authenticate and receive JWT token

### Athletes
- **POST /api/athletes** - Add a new athlete (COACH role)
- **PUT /api/athletes/{id}** - Update an athlete (COACH role)
- **GET /api/athletes** - Get all athletes (COACH or ANALYST role)
- **GET /api/athletes/{id}** - Get athlete by ID (COACH or ANALYST role)
- **DELETE /api/athletes/{id}** - Delete an athlete (COACH role)

### Performance Records
- **POST /api/performance-records/athlete/{athleteId}** - Add a new performance record (COACH role)
- **PUT /api/performance-records/{recordId}** - Update a performance record (COACH role)
- **DELETE /api/performance-records/{recordId}** - Delete a performance record (COACH role)
- **GET /api/performance-records/athlete/{athleteId}** - Get all records for an athlete (COACH or ANALYST role)
- **GET /api/performance-records/athlete/{athleteId}/metric/{metric}** - Get records for an athlete and metric (COACH or ANALYST role)
- **GET /api/performance-records/leaderboard/{metric}** - Get leaderboard for a metric (COACH or ANALYST role)

## Assumptions
- Each performance metric has an associated 'better-is-higher' or 'better-is-lower' direction. For example, lower times are better for sprints, while higher values are better for metrics like jumps or throws.
- Personal bests are determined by the highest value for each metric per athlete.
- Athletes can't have two performance records with the exact same metric and date — though not strictly enforced, it's recommended to avoid data redundancy.
- The app does not validate metric units, so it's up to the coach to ensure consistency (e.g., "speed" in km/h vs m/s).

## Performance Monitoring
The application uses Spring AOP to implement performance logging:
- Logs before and after adding performance records
- Logs exceptions thrown in the service layer
- Measures and logs execution time for critical operations

## How to Run the Application

### Prerequisites
- Java 21+
- Maven 3.6+
- MySQL running with a database named sports_tracker

### Steps
1. Clone the repository
   ```
   git clone <your-repo-url>
   cd sports-training-tracker
   ```

2. Configure Database
   Set your MySQL credentials in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/sports_tracker
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```

3. Build and Run
   ```
   mvn spring-boot:run
   ```

4. Access the API
   - API Documentation: http://localhost:8080/swagger-ui.html
   - Use Postman or any API client to interact with the endpoints

## Testing the API
1. Register a user:
   ```
   POST /auth/register
   {
     "username": "coach1",
     "password": "password123",
     "role": "COACH"
   }
   ```

2. Login to get JWT token:
   ```
   POST /auth/login
   {
     "username": "coach1",
     "password": "password123"
   }
   ```

3. Use the JWT token in the Authorization header for subsequent requests:
   ```
   Authorization: Bearer <your-jwt-token>
   ```

4. Test the endpoints:
   - Add athletes
   - Add performance records
   - View and analyze performance data