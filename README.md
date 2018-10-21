# Spring boot with REST API JPA and H2

An exercise project exposing API for manipulating Items in an inventory.
The project is a lightweight project which implements REST API, JPA-data implemented by hibernate
and a testing class.
Security - was not implemented.

In order to have initial data in the H2 DB 
added a file src/main/resources/data.sql with 4 items.
H2 recognize this file and runs the insert commands after startup.

Item structure:
	private Long number;
	private String name;
	private Long amount;
	private String inventorycode;

### Installing

1. Download the zip to your computer
2. unzip to a designated folder
2. from that folder run:
```
 mvn clean install
```

### Running the project

There are two main ways to run the application:
using java 

```
 java -jar .\boot-rest-h2-1.0.0.jar
```
using docker
first login to your docker hub account. 
```
docker login --username=<your user name>
```

then pull the docker image and run it
```
docker pull itamarlev/rest-h2:master
docker run -p 8080:8080 rest-h2
```

## SWAGGER Api catalogue 
when the application is running go to the following URL
to test the REST calls using swagger
```
http://localhost:8080/swagger-ui.html
```

