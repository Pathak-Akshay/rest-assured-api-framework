# Use OpenJDK 17
FROM openjdk:17-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy all project files into /app
COPY . .

# Give permission to gradlew
RUN chmod +x gradlew

# Pre-create test results folder with appropriate permissions
RUN mkdir -p build/test-results/test build/reports/tests/test && \
    chmod -R 777 build

# Build and run tests
# Use '-Dorg.gradle.daemon=false' instead of '--no-daemon' for better compatibility
CMD ["./gradlew", "test", "-Dorg.gradle.daemon=false", "--info"]