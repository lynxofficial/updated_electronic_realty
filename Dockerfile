FROM openjdk:21
COPY /target/*.jar /application.jar
CMD ["java", "-jar", "/application.jar"]