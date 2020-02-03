# Webselfie
This application is used to generate screenshots of websites based on provided URLs, returning images of the pages

# Requirements
- Java
- Maven
- Google Chrome
- PostgreSQL instance listening on port 5432 (you must create a database named **webselfie**, you can modify database properties in the **application.properties** file, located in '/src/main/resources')


# Install
In a terminal window, run the command **mvn clean install** 

# Run
In a terminal window, run the command **mvn spring-boot:run**, your application will be listening by default on **port 8080**

# Futher information
- Engineering Spec: [here](https://drive.google.com/open?id=1nUgPUltdoGU_ex8l361sGMcOf6dUUH_35h_duNNoeYE)
- Postman JSON collection for testing: [here](https://drive.google.comspri/open?id=1sFpfpSKLeDveonUTxguF0uf6X46__VfQ)