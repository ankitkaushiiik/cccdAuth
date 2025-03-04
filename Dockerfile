# Build stage
FROM maven:3.8.3-openjdk-8 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:8-jdk-alpine
WORKDIR /app
# Copy the jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Make port 6082 available outside container
EXPOSE 6082

# Run the jar file
ENTRYPOINT ["java","-jar","app.jar"] 