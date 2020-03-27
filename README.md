# InstaHelp API
Backend API for the InstaHelp mobile app.
## Author
* Mike Sanders
## Getting Started
### Prerequisites
This project was written using spring boot and is compatible with Java 1.8.
You will also need MySQL running with the user created that is specified in the ```bootStrap/db_init.sql``` file.
## Running the application
### Running the tests
The unit tests can be executed with the following command:

```./gradlew clean test```

### Using Gradle:
Run the following command from within the root directory:

```./gradlew bootRun```

This will start the application running on [localhost:8080](http://localhost:8080)
### Additional Information:
Swagger documentation is available at ```/swagger-ui.html``` or the Swagger api at ```/v2/api-docs```.