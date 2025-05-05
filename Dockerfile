# Use OpenJDK 17 (you can pick 17 or 21 based on what Jenkins/Docker needs)
FROM openjdk:17-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy all project files into /app
COPY . .

# Give permission to gradlew (important for Linux containers)
RUN chmod +x gradlew

# Pre-create test results folder to avoid missing dir issue
RUN mkdir -p build/test-results/test

# Build and run tests
CMD ["./gradlew", "clean", "test", "--no-daemon", "--info"]