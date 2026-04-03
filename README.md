# Tournax: Esports Tournament Management System

Welcome to **Tournax**, a robust Java Spring Boot backend API designed for managing esports tournaments. It provides a complete ecosystem for creating games, organizing tournaments, managing teams with their player rosters, and scheduling/executing matches.

---

## 🌟 Key Features

* **Role-Based Access Control (RBAC):** Secure API endpoints protected by Spring Security (Basic Authentication).
  * `ADMIN`: System administrators who create games, tournaments, schedule matches, and record match results.
  * `MANAGER`: Team owners who can create their own teams, update team names, and manage their player rosters.
  * `PLAYER`: Individual users who can join an open team or leave their current team.
* **Complete Entity Operations:** Full CRUD APIs for `User`, `Game`, `Tournament`, `Team`, and `Match`.
* **Data Integrity & Validation:** Built-in business logic enforcing rules such as (1) a player can only belong to one team at a time, and (2) a scheduled match must involve two distinct teams.
* **JSON Mapping Optimization:** Entity relationships are properly managed (`@JsonManagedReference` \& `@JsonBackReference`) to prevent bidirectional infinite recursion during serialization.

---

## 🏗 Architecture & Data Model

The application uses Hibernate/JPA for Object-Relational Mapping to effectively represent the real-world relationships of tournament management:

1. **User:** Every person (Admin, Manager, Player) is a User. Implement Spring Security `UserDetails`.
2. **Game:** Represents the competitive title (e.g., Valorant).
3. **Tournament:** A competition held for a specific Game.
4. **Team:** Managed by a Manager and composed of multiple Players.
5. **Match:** A contest between two distinct Teams within a specific Tournament.

### Entity Relationships:
* `Game` (1) ↔ (N) `Tournament`
* `Tournament` (1) ↔ (N) `Match`
* `User [Manager]` (1) ↔ (N) `Team`
* `Team` (1) ↔ (N) `User [Players]`
* `Team` (N) ↔ (M) `Match` (A Match explicitly has `team1`, `team2`, and optionally a `winner_team`)

---

## 🚀 Setup & Installation

### Prerequisites
* Java 17 or higher
* Maven
* MySQL / PostgreSQL (or whichever database you configure in `application.properties`)
* Postman (for API testing)

### Getting Started

1. **Clone the repository** (if you haven't already).
2. **Configure Database:** Ensure your database credentials in `src/main/resources/application.properties` are correct.
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/tournax_db?createDatabaseIfNotExist=true
   spring.datasource.username=root
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```
3. **Build the Application:**
   ```bash
   ./mvnw clean compile
   ```
4. **Run the Application:**
   ```bash
   ./mvnw spring-boot:run
   ```
   The application will start on `http://localhost:8080`.

---

## 🌐 API Overview

This API provides over 25+ endpoints organized by entity. Here is a high-level summary:

* **Authentication:**
  * `POST /auth/register` (Open to public)
* **Games (Requires ADMIN for write/delete):**
  * `GET /games`, `GET /games/{id}`, `POST /games`, `PUT /games/{id}`, `DELETE /games/{id}`
* **Tournaments (Requires ADMIN for write/delete):**
  * `GET /tournaments`, `GET /tournaments/{id}`, `POST /tournaments`, `PUT /tournaments/{id}`, `DELETE /tournaments/{id}`
* **Teams & Rosters (Requires MANAGER for write/delete, PLAYER for join/leave):**
  * `GET /teams`, `GET /teams/{id}`, `POST /teams`, `PUT /teams/{id}`, `DELETE /teams/{id}`
  * `POST /teams/{teamId}/join`, `DELETE /teams/{teamId}/leave`
  * `POST /teams/{teamId}/players/{playerId}`
* **Matches (Requires ADMIN for write/delete/results):**
  * `GET /matches`, `GET /matches/{id}`, `POST /matches`, `PUT /matches/{id}/result`, `DELETE /matches/{id}`

---

## 🧪 Testing with Postman

To fully exercise the API and demonstrate its flow, a comprehensive Postman collection containing 27 sequential requests is included in this repository. 

**How to use:**
1. Open Postman.
2. Import the collection from the `postman/collections/Tournax API Presentation` directory.
3. The requests are numbered sequentially from `1` to `27` and are grouped into logical steps:
   * **Step 1 - Authentication Phase:** Registering the Admin, Manager, and Player users.
   * **Step 2 - System Setup:** Creating Games.
   * **Step 3 - Tournament Generation:** Setting up Tournaments.
   * **Step 4 - Roster Construction:** Creating teams and assigning players.
   * **Step 5 - Tournament Execution:** Scheduling matches and recording the winner.

Run the requests in sequential order to observe the perfect, end-to-end flow of the Tournax system!
