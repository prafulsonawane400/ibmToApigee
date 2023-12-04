FROM openjdk:17-jdk-alpine AS builder

# Set working directory
WORKDIR /app

# Copy the application source code
COPY . .

# Build the application using Maven
RUN mvn clean package

# Create a slim image for production
FROM openjdk:17-jre-alpine

# Copy the built JAR file
COPY --from=builder /app/target/*.jar app.jar

# Expose the container port (usually 8080)
EXPOSE 8080

# Define environment variables (optional)
ENV SPRING_PROFILES_ACTIVE production

# Set entrypoint to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
