# Build stage
FROM maven:3.8.3-openjdk-8 AS build

COPY . .

RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:8-jdk-alpine

# Copy the jar from build stage
COPY --from=build /target/application-0.0.1-SNAPSHOT.jar application.jar

# Make port 6082 available outside container
EXPOSE 6082

# Run the jar file
ENTRYPOINT ["java","-jar","application.jar"] 