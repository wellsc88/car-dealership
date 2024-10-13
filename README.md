# Car Dealership

This project demonstrates unit and integration testing in a car dealership system. It provides RESTful APIs for managing cars.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [License](#license)

## Features

- RESTful APIs for car management.
- Unit and integration testing using JUnit and Mockito.
- Swagger UI for API documentation.
- Docker support for running MySQL.

## Technologies Used

- **Spring Boot**: Framework for building the application.
- **Spring Data JPA**: For data persistence and access.
- **MySQL**: Database for storing data.
- **JUnit**: Testing framework for unit and integration tests.
- **Mockito**: For mocking dependencies in tests.
- **Testcontainers**: For managing database instances during testing.
- **Swagger**: API documentation and testing interface.

## Getting Started

### Prerequisites

- Java 17 or later.
- Maven.
- MySQL (optional, can be run using Docker).

### Installation

Follow these steps to set up the project locally:

1. **Navigate to the project directory**: Change into the project directory with the following command:

   ```bash
    cd C:\MyProjectFolder
   ```

2. **Clone the repository**: Start by cloning the repository to your local machine. You can do this using the following command:

   ```bash
    git clone https://github.com/yourusername/car-dealership.git
   ```

### Configuration

Before running the application, you need to configure your database connection and other settings. Follow these steps:

1. **Database Configuration**:
   - Create a `.env` file in the root directory of the project.
   - Add your database connection details in the following format:
   - 
     ```yaml
     DB_URL=jdbc:mysql://localhost:3306/autodeal?useTimezone=true&serverTimezone=UTC
     DB_USERNAME=your_username
     DB_PASSWORD=your_password
     ```

2. **Application Properties**:
   - Ensure your `application.yml` or `application.properties` file is set to read these environment variables. For example, you can use:
     
     ```yaml
     spring:
       datasource:
         url: ${DB_URL}
         username: ${DB_USERNAME}
         password: ${DB_PASSWORD}
     ```

3. **Additional Configuration**:
   - If you're using any external services or APIs, make sure to configure their credentials and endpoints in the appropriate configuration files.

4. **Profiles**:
   - You can create different profiles (e.g., `dev`, `test`, `prod`) in your configuration files to manage settings for different environments.
   
### Running the Application

To run the application, follow these steps:

1. **Ensure the application is built**: Before running the application, make sure to build it using the command:

  ```bash
     mvn clean install
  ```

2. **Import the project into your IDE**: Open your favorite IDE (like IntelliJ IDEA, Eclipse, or VS Code) and import the project as a Maven project.

3. **Run the application**: You can run the application by executing the main class Startup in your IDE or by using the following Maven command:

  ```bash
   mvn spring-boot:run
  ```

4. **Access the application**: Once the application is running, you can access it at:

  ```bash
   http://localhost:8080
  ```

5. **Access Swagger UI**: Once the application is running, you can access the Swagger UI to explore the available API endpoints at:
  
  ```bash
    http://localhost:8080/swagger-ui/index.html
  ```

### Testing

To ensure the quality and reliability of the application, testing is implemented throughout the project. Follow these steps to run the tests:

1. **Run Unit Tests**: To execute the unit tests, use the following Maven command:
  
  ```bash
    mvn test
  ```

2. **Run Integration Tests**: To run integration tests, you can use the following command:

 ```bash
    mvn verify
  ```
3. **Coverage Reports**: If you are using JaCoCo for code coverage, you can find the coverage report in the target/site/jacoco directory after running the tests.

4. **Continuous Testing**: You can also configure your IDE or CI/CD pipeline to run tests automatically whenever you make changes to the codebase, ensuring that any potential issues are caught early.

### License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

By using this project, you agree to abide by the terms of the **Apache License 2.0** , which allows for reuse, modification, and distribution, provided that proper attribution is given to the original authors.

