FROM maven:latest AS build
COPY . /application
WORKDIR /application
RUN mvn clean package
FROM openjdk:21
COPY --from=build /application/target/*.jar /application.jar
CMD ["java", "-jar", "/application.jar"]