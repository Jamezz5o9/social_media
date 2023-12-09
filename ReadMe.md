# Social space

The Social space Application Backend is a comprehensive and robust backend solution built using Java 17, Spring Boot 3, Spring Security 6, PostgreSQL, offering seamless post creation, comment, follow and unfollow, and notification functionalities. 

## Key Features

- Java 17: The backend is developed using the latest Java 17 features, ensuring high performance, improved memory management, and enhanced developer productivity.

- Spring Boot 3: Spring Boot 3 provides a strong foundation for building microservices with its streamlined development approach, embedded server, auto-configuration, and powerful dependency management.

- Spring Security 6: Implementing Spring Security 6 ensures robust security measures, including user authentication and authorization, role-based access control, and protection against common security vulnerabilities.

- PostgreSQL Database: The PostgreSQL database system is used to store and manage various data entities, such as user profiles, tasks, notifications, and more, ensuring data integrity and scalability.

- RESTful APIs: The backend exposes a set of RESTful APIs that enable seamless communication between client applications and the server. These APIs provide endpoints for user management, task management, notifications, and more.

## Benefits

- Scalability: The modular architecture allows for easy scalability as new features can be added without disrupting existing components.

- Security: Spring Security 6 ensure robust protection against unauthorized access, while PostgreSQL safeguards user data.

- Notification System: Automated Emails for Email verifications, notification on post and comment and Notifications.

- Flexibility: Spring Boot's flexibility and Spring Security's customization capabilities enable the adaptation of the backend to various application requirements.

- Performance: Java 17's performance enhancements and Spring Boot's optimized configuration contribute to excellent runtime performance.

- Documentation: Comprehensive API documentation using tools like Swagger/OpenAPI ensures clear communication between frontend and backend developers and simplifies the integration process.

The TaskSpace Application Backend offers a modern, secure, and user-friendly backend solution for task management. Leveraging cutting-edge technologies and architectural principles, it empowers businesses and users to efficiently manage tasks, enhance productivity, and stay organized.
<br>

## Technologies Used
- Java (Programming language)
- Springboot (Framework used to develop the APIs)
- Maven (Dependency manager)
- PostgreSQL (Database for data storage)
- JWT (Library for authentication)
- Spring Security (Framework used for security)

## Prerequisites

To build and run this project, you'll need:

- Java JDK 17 or later
- Spring Boot 3.0
- Maven 3.0 or later

## Features

- User authentication and authorization using Spring Security 
- CRUD operations for managing post, comment etc 
- PostgreSQL database integration 
- API documentation using Swagger
- JWT-based token authentication

## Getting Started

To get started with Social space, you will need to clone this repository to your local machine and set up the necessary dependencies.

### Installation

1. Clone this repository to your local machine:

    ```bash
    git clone https://github.com/Jamezz5o9/social_media.git
    ```

2. Create PostgreSQL database

   ```bash
   psql> create database social_space
   ```

3. Configure database username and password

     ```properties
   # This is optional if you're testing the app in localhost. A demo database is already provided.
   # src/main/resources/application.yml
     spring:
       datasource:
         url: jdbc:postgresql://localhost:5432/social_space
         username: <YOUR_DB_USERNAME>
         password: <YOUR_DB_PASSWORD>

     ```
4. Set up the backend server:
   ```bash
     mvn spring-boot:run
   ```

## Swagger Authentication Endpoints

   ```bash
     (Swagger) http://localhost:9000/swagger-ui/index.html#
     
     The above Url routes to the swagger UI for testing.
     The Authentication groups relates to Registering with Email, Password and name
     The Social space group relates to the Basic CRUD operations for the case study
     http://localhost:9000/?token={token}
   ```

## Functional requirement

- User story: I can register a new account
- User story: I can log in
- User Story: I can post
- User story: I can comment
- User story: I can follow
- User story: I can unfollow
- User story: I can like
- User story: I can unlike

## Non-Functional Requirements

The following non-functional requirements must be met:

- Security: The application has robust security measures in place to protect user data and prevent unauthorized access.
- Availability: The application is highly available, with minimal downtime and interruptions in service.
- Performance: The application is optimized for low latency, with fast response times to user requests.