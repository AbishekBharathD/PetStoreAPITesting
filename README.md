# PetStoreAPITesting

## Introduction

PetStoreAPITesting is a project designed to automate the testing of the Pet Store API. It leverages Java, TestNG, and Rest Assured to provide a robust and maintainable framework for validating API endpoints. The repository includes comprehensive test cases, configuration management, and structured reporting to ensure the quality of the Pet Store API.

## Features

- Automated test suite for Pet Store API endpoints
- Use of TestNG for test orchestration and reporting
- Utilization of Rest Assured for HTTP requests and response validation
- Centralized configuration for easy environment management
- Data-driven testing using external excel files
- Assertion of HTTP status codes and response payloads
- Structured test logging and reporting

## Requirements

- Java Development Kit (JDK) 8 or higher
- Maven 3.x or higher
- Internet connection (for Maven dependencies)
- Any IDE that supports Java and Maven (e.g., VS Code, Eclipse)

## Installation

Follow these steps to set up the project:

```steps
1. Clone the repository | `git clone https://github.com/AbishekBharathD/PetStoreAPITesting.git`
2. Navigate to the project directory | `cd PetStoreAPITesting`
3. Install dependencies | `mvn clean install`
```

> [!NOTE]
> Maven will automatically download all required dependencies during the installation process.

## Usage

To run the test suite, use the following command from the project root:

```bash
mvn test
```

TestNG will execute all test cases, and results will be available in the `target/ExtentReports` directory.

### Running Specific Test Classes

To run a specific test class, use:

```bash
mvn -Dtest=ClassName test
```

For example:

```bash
mvn -Dtest=TestRunner test
```

### Test Reports

After running the tests, review generated reports at `target/ExtentReports` for detailed outcomes.

## Configuration

The project includes configuration files to manage environment settings and test data.

- `config.properties`: Stores base URLs and other environment variables.
- `testdata/`: Contains Excel files with test payloads.

You can modify `config.properties` to point to different environments or update the test data files as needed.

> [!TIP]
> Keep your API base URL up to date in the configuration file for seamless switching between environments.

## API Endpoints

### Get Pet by ID (GET /pet/{petId})

#### Retrieve a pet by its ID

```api
{
    "title": "Get Pet by ID",
    "description": "Retrieves a specific pet using pet ID.",
    "method": "GET",
    "baseUrl": "https://petstore.swagger.io/v2",
    "endpoint": "/pet/{petId}",
    "headers": [
        {
            "key": "Accept",
            "value": "application/json",
            "required": true
        }
    ],
    "pathParams": [
        {
            "key": "petId",
            "value": "ID of the pet to fetch",
            "required": true
        }
    ],
    "bodyType": "none",
    "responses": {
        "200": {
            "description": "Successful response with pet details",
            "body": "{\n  \"id\": 123,\n  \"name\": \"Doggie\",\n  \"status\": \"available\"\n}"
        },
        "404": {
            "description": "Pet not found",
            "body": "{\n  \"message\": \"Pet not found\"\n}"
        }
    }
}
```

### Add a New Pet (POST /pet)

#### Add a new pet to the store

```api
{
    "title": "Add New Pet",
    "description": "Adds a new pet to the Pet Store.",
    "method": "POST",
    "baseUrl": "https://petstore.swagger.io/v2",
    "endpoint": "/pet",
    "headers": [
        {
            "key": "Content-Type",
            "value": "application/json",
            "required": true
        }
    ],
    "bodyType": "json",
    "requestBody": "{\n  \"id\": 123,\n  \"category\": { \"id\": 1, \"name\": \"Dogs\" },\n  \"name\": \"Doggie\",\n  \"photoUrls\": [\"string\"],\n  \"tags\": [{\"id\": 0, \"name\": \"tag1\"}],\n  \"status\": \"available\"\n}",
    "responses": {
        "200": {
            "description": "Pet added successfully",
            "body": "{\n  \"id\": 123,\n  \"name\": \"Doggie\",\n  \"status\": \"available\"\n}"
        },
        "405": {
            "description": "Invalid input",
            "body": "{\n  \"message\": \"Invalid input\"\n}"
        }
    }
}
```

### Update an Existing Pet (PUT /pet)

#### Update details of an existing pet

```api
{
    "title": "Update Pet",
    "description": "Updates an existing pet in the store.",
    "method": "PUT",
    "baseUrl": "https://petstore.swagger.io/v2",
    "endpoint": "/pet",
    "headers": [
        {
            "key": "Content-Type",
            "value": "application/json",
            "required": true
        }
    ],
    "bodyType": "json",
    "requestBody": "{\n  \"id\": 123,\n  \"category\": { \"id\": 1, \"name\": \"Dogs\" },\n  \"name\": \"Doggie Updated\",\n  \"photoUrls\": [\"string\"],\n  \"tags\": [{\"id\": 0, \"name\": \"tag1\"}],\n  \"status\": \"sold\"\n}",
    "responses": {
        "200": {
            "description": "Pet updated successfully",
            "body": "{\n  \"id\": 123,\n  \"name\": \"Doggie Updated\",\n  \"status\": \"sold\"\n}"
        },
        "400": {
            "description": "Invalid ID supplied",
            "body": "{\n  \"message\": \"Invalid ID supplied\"\n}"
        },
        "404": {
            "description": "Pet not found",
            "body": "{\n  \"message\": \"Pet not found\"\n}"
        }
    }
}
```

### Delete a Pet (DELETE /pet/{petId})

#### Delete a pet by its ID

```api
{
    "title": "Delete Pet",
    "description": "Deletes a pet from the store by its ID.",
    "method": "DELETE",
    "baseUrl": "https://petstore.swagger.io/v2",
    "endpoint": "/pet/{petId}",
    "headers": [
        {
            "key": "Accept",
            "value": "application/json",
            "required": true
        }
    ],
    "pathParams": [
        {
            "key": "petId",
            "value": "ID of the pet to delete",
            "required": true
        }
    ],
    "bodyType": "none",
    "responses": {
        "200": {
            "description": "Pet deleted successfully",
            "body": "{\n  \"message\": \"Pet deleted\"\n}"
        },
        "404": {
            "description": "Pet not found",
            "body": "{\n  \"message\": \"Pet not found\"\n}"
        }
    }
}
```

## Contributing

Contributions are welcome. To contribute:

```steps
1. Fork the repository
2. Create a new branch for your feature or bugfix
3. Commit your changes with clear messages
4. Push your branch to your fork
5. Open a pull request describing your changes
```

> [!IMPORTANT]
> Ensure all test cases pass before submitting a pull request.

---

Thank you for using PetStoreAPITesting! For issues or inquiries, please use the GitHub issue tracker.
