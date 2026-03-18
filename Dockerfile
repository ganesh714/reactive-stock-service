# Use an official, lightweight Java 17 runtime as the base image
# (If you chose Java 21 in Spring Initializr, change the 17 to 21)
FROM eclipse-temurin:17-jre-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file from your target folder into the container
# We rename it to 'app.jar' for simplicity
COPY target/*SNAPSHOT.jar app.jar

# Expose the port your application runs on
EXPOSE 8080

# The command to run when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]