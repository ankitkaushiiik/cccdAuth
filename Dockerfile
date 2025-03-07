# Build stage (Maven with JDK 8)
FROM maven:3.8.3-openjdk-8 AS build
WORKDIR /app

# Copy pom.xml and download dependencies first (for caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source files and build the app
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage (Using a stable OpenJDK 8 runtime)
FROM eclipse-temurin:8-jdk
WORKDIR /app
COPY --from=build /app/target/CCDAuthenticationServer-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 6082
ENTRYPOINT ["java", "-jar", "app.jar"]
