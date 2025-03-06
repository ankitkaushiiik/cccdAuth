# Build stage
FROM eclipse-temurin:8-jdk AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Run stage
FROM eclipse-temurin:8-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/CCDAuthenticationServer-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 6082
ENTRYPOINT ["java","-jar","app.jar"] 