# ---- Stage 1: Build ----
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Copy all files to the container
COPY . .

# Build the Spring Boot project (skip tests)
RUN mvn clean package -DskipTests

# ---- Stage 2: Run ----
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy only the built jar from the builder stage
COPY --from=builder /app/target/Eduplanner-0.0.1-SNAPSHOT.jar app.jar

# Expose port (optional; Railway auto-detects ports)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
