pipeline {
    agent {
        label 'windows-docker-agent'  // Update this to match your actual agent label
    }

    environment {
        IMAGE_NAME = "rest-assured-api-framework"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Pathak-Akshay/rest-assured-api-framework.git'
            }
        }

        stage('Clean') {
            steps {
                // Clean build directory before starting
                sh 'rm -rf build || true'
                sh 'mkdir -p build'
                sh 'chmod -R 777 build'
                sh 'ls -la'  // Debug: List current directory
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ${IMAGE_NAME} .'
            }
        }

        stage('Run Tests') {
            steps {
                // Run tests and mount volume with absolute path to ensure proper mounting
                sh '''
                    echo "Current directory: $(pwd)"
                    docker run --rm \
                      -v "$(pwd)/build:/app/build" \
                      --name test-container \
                      ${IMAGE_NAME}

                    # Check docker exit code
                    DOCKER_EXIT=$?
                    echo "Docker exited with code: $DOCKER_EXIT"

                    # Copy files from container if they weren't properly mounted
                    if [ ! -f "build/reports/tests/test/index.html" ] && [ $DOCKER_EXIT -eq 0 ]; then
                      echo "Test results not found in mounted volume, attempting to copy from container..."
                      docker create --name temp-container ${IMAGE_NAME}
                      docker cp temp-container:/app/build/. ./build/
                      docker rm temp-container
                    fi
                '''
            }
        }

        stage('Check Test Results') {
            steps {
                // Debug steps to verify files exist
                sh 'echo "Contents of build directory:"'
                sh 'find build -type f | sort || echo "No files found in build directory"'
            }
        }
    }

    post {
        always {
            // Archive HTML test report
            archiveArtifacts artifacts: 'build/**', allowEmptyArchive: true, fingerprint: true

            // Optionally, print a message to locate report
            echo 'Test artifacts archived. You can download and view index.html from the build artifacts.'
        }
    }
}