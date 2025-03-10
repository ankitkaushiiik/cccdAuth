# Use an official JDK 8 runtime as a parent image
FROM openjdk:8-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the Maven build files and the application JAR
COPY target/*.jar app.jar

# Expose the application port (default for Spring Boot is 8080)
EXPOSE 8080
# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]