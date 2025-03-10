# Build stage (Maven with JDK 18)
FROM 3.8.6-openjdk-8 AS build
WORKDIR /app

# Optimize dependency caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source files and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage (Using OpenJDK 18)
FROM eclipse-temurin:8-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/CCDAuthenticationServer-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 6082
ENTRYPOINT ["java", "-jar", "app.jar"]
