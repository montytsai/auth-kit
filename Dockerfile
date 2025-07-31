# --- Stage 1: Build Environment ---
# Use a specific Maven and JDK version for a reproducible build environment.
# Name this stage 'builder' for later reference.
FROM maven:3.9.6-eclipse-temurin-17-focal AS builder

# Set the working directory inside the container.
WORKDIR /app

# Copy pom.xml first to leverage Docker's layer caching.
# Dependencies will only be re-downloaded if pom.xml changes.
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code.
COPY src ./src

# Package the application, skipping tests for a faster build in the Docker context.
RUN mvn package -DskipTests

# --- Stage 2: Production Environment ---
# Use a minimal JRE base image for a smaller footprint and improved security.
FROM eclipse-temurin:17-jre-focal

# Set the working directory for the final image.
WORKDIR /app

# Copy only the built artifact from the 'builder' stage.
# This is the core of the multi-stage build, ensuring the final image is lean.
COPY --from=builder /app/target/auth-kit-*.jar app.jar

# Document that the application listens on port 8080.
EXPOSE 8080

# Define the command to run when the container starts.
# The exec form is used for proper signal handling.
ENTRYPOINT ["java", "-jar", "app.jar"]