# Use an official OpenJDK image as the base image
FROM openjdk:23-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build/libs directory to the container
COPY build/libs/Personal_Finance_Tracker_API.jar app.jar

# Expose the port that the application will run on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
