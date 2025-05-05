# Use OpenJDK 17
FROM openjdk:17-jdk-slim

# Install necessary tools for debugging
RUN apt-get update && apt-get install -y procps

# Set working directory inside container
WORKDIR /app

# Copy all project files into /app
COPY . .

# Give permission to gradlew
RUN chmod +x gradlew

# Pre-create test results folder with appropriate permissions
RUN mkdir -p build/test-results/test build/reports/tests/test && \
    chmod -R 777 build

# Create a wrapper script to run tests and ensure output goes to the right place
RUN echo '#!/bin/sh\n\
echo "Starting test execution..."\n\
./gradlew test -Dorg.gradle.daemon=false --info\n\
EXIT_CODE=$?\n\
echo "Test execution completed with exit code: $EXIT_CODE"\n\
echo "Checking for test results:"\n\
ls -la build/\n\
ls -la build/reports/ || echo "No reports directory found"\n\
ls -la build/reports/tests/ || echo "No tests directory found"\n\
ls -la build/reports/tests/test/ || echo "No test results found"\n\
echo "Ensuring proper permissions on output files:"\n\
chmod -R 777 build/\n\
exit $EXIT_CODE' > /app/run-tests.sh && chmod +x /app/run-tests.sh

# Run the wrapper script as the entry point
CMD ["/app/run-tests.sh"]