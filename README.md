# Spring boot with REST API JPA and H2

An exercise project exposing API for manipulating Items in an inventory.
The project is a lightweight project which implements REST API, JPA-data implemented by hibernate
and a Testing class.
Security - was not implemented.

In order to have initial data in the H2 DB 
Added a file src/main/resources/data.sql with 4 items.
H2 recognize this file and runs the insert commands after startup.

Item structure:
	private Long number;
	private String name;
	private Long amount;
	private String inventorycode;

### Installing

1. Download to your computer
2. from the root folder run:
```
 mvn clean install
```

### Running the project

There are two main ways to run the application:
using java 

```
 java -jar .\target\boot-rest-h2-0.0.1-SNAPSHOT.jar
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

## Running the tests
In order to the the system it's best to use REST client as Postman.

GET requests:

get all the Items in the DB.
```
http://localhost:8080/items
```
get a specific object by its number ID. (ex:2)
http://localhost:8080/items/{number}
```
http://localhost:8080/items/2
```

POST requests: (not idempotent)

Deposit a number of items (ex: 2;3)
http://localhost:8080/items/{number}/withdraw/{amount}
```
http://localhost:8080/items/2/withdraw/3
```

Withdraw a number of items (ex: 2;3)
http://localhost:8080/items/{number}/withdraw/{amount}
```
http://localhost:8080/items/2/deposit/3
```

Add a new Item
http://localhost:8080/items/
```
http://localhost:8080/items/

in the body:
{
    "name": "new item",
    "amount": 43,
    "inventoryCode": "HF56478"
}
```
PUT requests: (idempotent)
update an items (ex: 2;3)
http://localhost:8080/items/{number}
```
http://localhost:8080/items/2/

in the body:
{
    "name": "updated item",
}
```
