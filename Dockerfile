# Build stage
FROM maven:3.8.3-openjdk-8 AS build

COPY . .

RUN mvnw clean package -DskipTests

# Run stage
FROM openjdk:8-jdk-alpine
WORKDIR /app
# Copy the jar from build stage
COPY --from=build /target/CCDAuthenticationServer-0.0.1-SNAPSHOT.jar /app

# Make port 6082 available outside container
EXPOSE 6082

# Run the jar file
CMD ["java","-jar","CCDAuthenticationServer-0.0.1-SNAPSHOT.jar"] 