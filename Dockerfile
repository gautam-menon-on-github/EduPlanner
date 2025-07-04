# Use a lightweight OpenJDK base image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the jar file from your local machine into the container
COPY target/Eduplanner-0.0.1-SNAPSHOT.jar app.jar

# Command to run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
