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

### Run Locally
In order to run the application locally you must use the ```local``` active profile.

### Run With Messaging Enabled
If you want to enable SMS messaging, you will need to run the app with the ```twilio``` active profile as well.
 
You will also need to the following environment variables set:
* ```TWILIO_ACCOUNT_SID```
* ```TWILIO_ACCOUNT_AUTH_TOKEN```
* ```TWILIO_ACCOUNT_PHONE_NUMBER```

The values for these variables can be obtained by setting up a developer account on the [Twilio website](https://www.twilio.com/try-twilio).

### Additional Information:
Swagger documentation is available at ```/swagger-ui.html``` or the Swagger api at ```/v2/api-docs```.