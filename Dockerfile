# Use OpenJDK 17
FROM openjdk:17-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy all project files into /app
COPY . .

# Give permission to gradlew
RUN chmod +x gradlew

# Pre-create test results folder with appropriate permissions
RUN mkdir -p build/test-results/test build/reports/tests/test

# Build and run tests
CMD ["./gradlew", "test", "-Dorg.gradle.daemon=false", "--info"]