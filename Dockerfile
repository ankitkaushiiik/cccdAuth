# Build stage
FROM maven:3.8.3-openjdk-8 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/CCDAuthenticationServer-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 6082
ENTRYPOINT ["java","-jar","app.jar"] 