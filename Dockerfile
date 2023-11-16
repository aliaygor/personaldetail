# Docker base image
FROM maven:3.8-openjdk-17-slim

# Set working directory
WORKDIR /app

# Copy the project files into the container
COPY . /app

# Build the application with Maven
RUN mvn clean package

# Specify the JAR file to run on container startup
ENTRYPOINT ["java", "-jar", "target/personal-0.0.1-SNAPSHOT.jar"]
