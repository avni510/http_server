Java HTTP Server
---------------

HTTP Server written in Java

Requirements
-----------
Java 1.8.0

JUnit 4.12

Gradle 3.5

Installation
-----------

Clone the git repository
```
$ https://github.com/avni510/http_server.git
```

`cd` into the directory
```
$ cd http_server
```

Build and Run the Cobspec App
--------------
Build the JAR
```
$ gradle cobspec
```

Start the server with default port number 4444 and directory "/code"
```
$ java -jar cobspec/build/libs/cobspec-standalone.jar
```

To specify port number and directory
```
$ java -jar cobspec/build/libs/cobspec-standalone.jar -p <port number> -d <directory path>
```
Valid port numbers are between 0 and 65535

Navigate in your browser to `http://localhost:<port number>`

`CTRL+C` to quit the server

Build and Run the RESTful App
--------------
Build the JAR
```
$ gradle restful
```

```
$ java -jar restful/build/libs/restful-standalone.jar
```

All RESTful routes related to "/users" are valid

Navigate in your browser to `http://localhost:4444`

`CTRL+C` to quit the server

Run the Tests
-------------
To run all tests
```
$ ./gradlew test
```

To run cobspec tests
```
$ ./gradlew cobspec:test
```

To run restful tests
```
$ ./gradlew restful:test
```
