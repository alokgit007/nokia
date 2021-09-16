**Title: Implement notification emitter Service using microservice architecture and deploy minikube environment using docker and kubernetes tools**

 Design a record importer and notification emitter micro service:

Record can be details of the student, employee or bank details of people
Design importer services that takes bulk records as json input and parses each record and store it in database.
Expose an endpoint/api to import records and modify records
Whenever records are modified then kafka notification should be emitted
 

Database: Casandra/mariadb/ any preferred
Messaging: Kafka or any preferred
Language: Go / Java / C / C++

Non-Function Requirements: 

Documentation using Swagger
Performance Metrics
Docker-compose tests
The code should be compiled and deployable in minikube

**Running Examples:**
Download the code or clone the Git repository.
Open Command Prompt and Change directory (cd) to folder containing pom.xml
Open Eclipse
File -> Import -> Existing Maven Project -> Navigate to the folder where you have the code
Select the right project
Choose the Spring Boot Application file (search for @SpringBootApplication)
Right Click on the file and Run as Java Application
You are all Set
