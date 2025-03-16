# Step 1: Use Maven to build the JAR
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy only the pom.xml first to cache dependencies
COPY pom.xml .

# Download dependencies first (without building the project)
RUN mvn dependency:go-offline -DskipTests

# Now copy the rest of the source code
COPY src src

# Build the project (with cached dependencies)
RUN mvn clean package -DskipTests

# Step 2: Use a lightweight JDK runtime for deployment
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/User-Details-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]