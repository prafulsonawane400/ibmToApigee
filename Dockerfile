FROM openjdk:8-jdk-alpine

# Copy your application JAR file to the container
COPY target/.jar app.jar

# Expose the container port (usually 8080)
EXPOSE 8080


# Set entrypoint to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]
