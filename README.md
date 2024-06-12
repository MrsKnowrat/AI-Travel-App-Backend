# Capstone-Backend
# TravelActivityApp README

## Overview
TravelActivityApp is a Java-based application designed to AI-generate travel itineraries, activities, and tags associated with those activities. It allows users to create, update, and retrieve AI-generated itineraries along with their associated activities and tags. With the AI-derived responses, users can obtain personalized itinerary guidance based on any location they choose, types of travel companions and their needs and preferences. The Java & Spring Boot-based backend application works in tandem with a JavaScript & React-based frontend application to complete the user experience. The AI-generated content is sourced from OpenAI APIs.

## Features
- **Create Itineraries**: Users can create travel itineraries specifying their itinerary name, description of the trip (including location, needs, preferences, what type of travelers are participating, start and end dates.
- **Manage Activities**: Activities are added to itineraries, each with its own set of tags.
- **User Authentication**: Users can register and authenticate to manage their personal itineraries.
- **API Integration**: The application integrates with an external API (OpenAI) to fetch suggested activities based on user preferences.

## Technologies Used
- **Spring Boot**: Framework for creating stand-alone, production-grade Spring based applications.
- **Java Persistence API (JPA)**: For database interaction.
- **PostgreSQL**: As the relational database.
- **Lombok**: To reduce boilerplate code in Java classes.
- **ModelMapper**: For object mapping.
- **Spring Security**: For authentication and authorization.
- **React**: For building a dynamic frontend to work in tandem with this app.

## Product Development Plan
[Project Plan](https://docs.google.com/spreadsheets/d/1LJ4Vg0oFXCdw2MbK_QIZC1KIO36yN9MZ4ZBGtr_k8pM/edit?usp=sharing)

## Setup and Installation
1. **Clone the repository**:
```bash git clone gh repo clone git.generalassemb.ly/mrsknowrat/Capstone-Backend; or https://git.generalassemb.ly/mrsknowrat/Capstone-Backend.git```
2. **Navigate to the project directory**: 
```bash cd TravelActivityApp```
3. **Build the project**: ```bash mvn clean install```
4. **Postgres SQL**: 
Create and name SQL database inside your pgAdmin platform. 
5. **Connect your app with Postgres**:
In the application.properties file, use the following setup:

spring.application.name=TravelActivityApp
server.port=8080

PostgreSQL configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/travelapp
spring.datasource.username=postgres

JPA and Hibernate configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

6. **Postgres security in IntelliJ**:
For security reasons, do not store your postgres password here. Instead, please follow these instructions on how to hide your password inside IntelliJ, which will not be included in git pushes into GitHub:

In the intellij toolbar at the top of your screen:
Click on Run → Edit Configuration → Modify Options → Click Environmental Variables
→ enter: spring.datasource.password=yourPassword → press apply & ok

7. **API Key Security**:
Before pushing code to your GitHub repository, create a Config.Properties file in this location: src/main/resources/config.properties. Store your API Key in this file. Then, for security, add "src/main/resources/config.properties" to your .gitignore file. This will ensure that code pushes will ignore your config properties to keep sensitive data out of version control.  
8. **Run the application**:
bash: mvn spring-boot:run; or simply click the green triangle at the top right-hand corner of IntelliJ.

## Dependencies

1. **Spring Boot Starter Data JPA**
    - **Artifact ID**: `spring-boot-starter-data-jpa`
    - **Purpose**: Simplifies the development of applications that access databases using Java Persistence API (JPA), including support for Hibernate and database transaction management.

2. **Spring Boot Starter Web**
    - **Artifact ID**: `spring-boot-starter-web`
    - **Purpose**: Used for building web applications with Spring MVC, includes Tomcat as the default embedded container.

3. **Spring Boot Devtools**
    - **Artifact ID**: `spring-boot-devtools`
    - **Purpose**: Provides fast application restarts, LiveReload, and enhanced development experience. Recommended only for development environments.

4. **PostgreSQL JDBC Driver**
    - **Artifact ID**: `postgresql`
    - **Purpose**: JDBC driver for PostgreSQL to connect Java programs to a PostgreSQL database using standard, database-independent Java code.

5. **Project Lombok**
    - **Artifact ID**: `lombok`
    - **Purpose**: Reduces boilerplate code in Java applications by using annotations for common tasks like generating getters, setters, and other typical methods.

6. **Spring Boot Starter Test**
    - **Artifact ID**: `spring-boot-starter-test`
    - **Purpose**: Provides essential libraries for testing Spring Boot applications, including JUnit, Spring Test, and Mockito.

7. **Spring Boot Starter Validation**
    - **Artifact ID**: `spring-boot-starter-validation`
    - **Purpose**: Supports validation using the Java Bean Validation API, ensuring data conforms to rules before processing.

8. **ModelMapper**
    - **Artifact ID**: `modelmapper`
    - **Purpose**: Simplifies the process of mapping data between data transfer objects (DTOs) and domain models.

9. **Spring Boot Starter Security**
    - **Artifact ID**: `spring-boot-starter-security`
    - **Purpose**: Adds security features to the application, including authentication, authorization, and protection against common vulnerabilities.

10. **Jackson Datatype JSR310**
    - **Artifact ID**: `jackson-datatype-jsr310`
    - **Purpose**: Supports serialization and deserialization of Java 8 Date and Time API (java.time) objects using Jackson.


## API Endpoints
- **Signup New User**: `POST /auth/signup`
- **Login Existing User**: `POST /auth/login`
- **Get User By Username**: `GET /users/username/{username}`
- **Update User**: `GET /users/{username}`
- **Create Itinerary**: `POST /itineraries`
- **Receive Itinerary Data & API Key**: `POST /itineraries/processApiResponse`
- **Update/Save Itinerary**: `PUT /itineraries//{id}`
- **Get Itinerary By Username**: `GET /itineraries/user/{username}`
- **Delete Itinerary**: `GET /itineraries/user/{userId}`
- **Create Activity**: `POST /activities`
- **Update Activity**: `PUT /activities/{id}`
- **Delete Activity**: `DELETE /activities/{id}`

## Configuration
Ensure you have the `config.properties` file in your resources directory with the necessary API keys and database configurations.

## Author
- **Erica Sowder** - *Initial work* - [mrsknowrat](https://git.generalassemb.ly/mrsknowrat)

## Contributing
Contributions are welcome! Please feel free to submit a pull request or create an issue if you have suggestions for improvements or have identified bugs.

## Acknowledgments
- Hat tip to Bereket Beshane for providing demonstration code that inspired some of the formatting of my backend architecture.
- Big thanks to Bereket Beshane and Asha Mathis for taking me from level 0 of coding skill to where I am now. I am very grateful!
