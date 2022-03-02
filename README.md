# spring-cloud-kafka-streams-exception

Sample Application to test StreamsUncaughtExceptionHandler


CD to docker directory and start the kafka server
```bash
cd docker
docker-compose -f docker-compose.yml up -d
```

This command start the kafka server on localhost:29092

### Start the service

#### Java

```bash
mvn clean package
java -jar target/spring-cloud-kafka-streams-exception.jar
```

#### Maven

```bash
mvn clean spring-boot:run 
```

#### IDE

Can run as a Spring-Boot application 

Application creates necessary topics during the boot-up.
A scheduled method in the MessageScheduler class sends messages to the "inTopic" in every 5 seconds.


