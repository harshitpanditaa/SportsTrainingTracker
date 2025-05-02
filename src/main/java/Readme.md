<h1>Project Report: Sports Training Performance Tracker</h1>
The app tracks athlete performance using a simple but powerful structure:

<u><h3>Athlete</h3></u>

id (Long): Unique identifier

name (String): Full name of the athlete

sport (String): The sport they’re training in (e.g., sprinting, swimming)

Relation: One athlete → many performance records

PerformanceRecord

id (Long): Unique record ID

athlete (Athlete): Linked to a specific athlete

metric (String): Type of measurement (e.g., "speed", "stamina", "lap time")

value (Double): The recorded value for the metric

date (LocalDate): When the performance was recorded

"Assumption: Each performance metric has an associated 'better-is-higher' or 'better-is-lower' direction defined per metric. For example, lower times are better for sprints, while higher values are better for metrics like jumps or throws."
Personal Bests are determined by the highest value for each metric per athlete.

Athletes can't have two performance records with the exact same metric and date — though not strictly enforced, it's recommended to avoid data redundancy.

The app does not validate metric units, so it's up to the coach to ensure consistency (e.g., “speed” in km/h vs m/s).

How to Run the Application
Prerequisites
Java 17+

Maven 3.6+

MySQL running with a database named sports_tracker

Steps
Clone the repo

bash
git clone <your-repo-url>
cd sports-training-tracker
Configure DB
Set your MySQL credentials in application.properties:

properties
spring.datasource.url=jdbc:mysql://localhost:3306/sports_tracker
spring.datasource.username=root
spring.datasource.password=your_password
Build & Run

bash
mvn spring-boot:run
Test It Out
Use Postman or your favorite API tool to hit:

POST /athletes → Add athletes

POST /records → Add performance records

GET /athletes/{id}/bests → View personal bests

GET /athletes/compare → Compare two athletes on a metric