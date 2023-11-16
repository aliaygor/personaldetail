# Docker base image
FROM openjdk:17-jdk

# Set working directory
WORKDIR /app

# Copy the project files into the container
COPY . /app

# Build the application with Maven
RUN ./mvnw clean package

# Specify the JAR file to run on container startup
ENTRYPOINT ["java", "-jar", "target/personaldetail.jar"]
