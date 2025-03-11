FROM openjdk:17-jdk-slim
# Use Java 17 lightweight base image
WORKDIR /app
# Set working directory in container
COPY target/User-Details-0.0.1-SNAPSHOT.jar app.jar
# Copy the built Spring Boot JAR file
EXPOSE 8080
# Expose port 8080 for the application
ENTRYPOINT ["java", "-jar", "app.jar"]
# Command to run the app