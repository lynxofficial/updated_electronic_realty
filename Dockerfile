FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/ElectronicRealty-1.0-SNAPSHOT.jar application.jar
ENTRYPOINT ["java", "-jar", "/application.jar"]