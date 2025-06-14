# Recipe Sharing App
A web application for sharing, rating, and searching recipes, built with a Spring Boot REST API and an HTML/JavaScript frontend.

## Features
- User authentication with JWT (register/login/logout)
- Create, update, delete, and rate recipes (1-5 stars)
- Search recipes by name/ingredients and filter by cuisine
- Strong password policy and login rate limiting
- SQL Server database for persistent storage
- Logging to console and file (logs/recipe-app.log)

## Setup

1. Prerequisites:
- Java 21, Maven, SQL Server (create database RecipeDB)
- SQL Server credentials (update application.properties)

2. Backend:
- Clone the repository and navigate to the project root
- Update application.properties with SQL Server credentials and a secure jwt.secret
- Run: mvn spring-boot:run (starts on http://localhost:8080)

3. Frontend:
- Serve index.html using a local server (e.g., VS Code Live Server or python -m http.server)
- Ensure CORS is configured for your frontend URL (e.g., http://localhost:63342)

## Usage

- Register with a strong password (8+ characters, uppercase, lowercase, number, special character)
- Log in (5 attempts/minute limit)
- Add, rate, or delete recipes (only owners can delete)
- Search by name/ingredients or filter by cuisine
- Check logs/recipe-app.log for audit logs

## Notes
- Replace trustServerCertificate=true with SSL certificates in production
- Adjust log levels in application.properties for debugging

