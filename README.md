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
Start the server
```
$ java -jar build/libs/http_server.jar
```

`CTRL+C` to quit the server

The server will listen on port 4444

Run the Tests
-------------
```
$ ./gradlew test
```
