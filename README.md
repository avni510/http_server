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

Build the JAR with gradle wrapper
```
$ ./gradlew build
```

Run the Server
--------------
Start the server with default port number 4444 and directory "/code"
```
$ java -jar build/libs/http_server.jar
```

To specify port number and directory
```
$ java -jar build/libs/http_server.jar -p <port Number> -d <directory path>
```
Valid port numbers are between 0 and 65535

`CTRL+C` to quit the server

Run the Tests
-------------
```
$ ./gradlew test
```
