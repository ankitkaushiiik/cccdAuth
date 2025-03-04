# Build stage
FROM maven:3.8.3-openjdk-8 AS build
ARG JAR_FILE=target/*.jar
COPY pom.xml .
COPY ${JAR_FILE} CCDAuthenticationServer.jar
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:8-jdk-alpine
# Copy the jar from build stage
#COPY --from=build /CCDAuthenticationServer/target/*.jar /CCDAuthenticationServer/

# Make port 6082 available outside container
EXPOSE 6082

# Run the jar file
ENTRYPOINT ["java","-jar","/CCDAuthenticationServer.jar"] 