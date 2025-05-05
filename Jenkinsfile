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
                sh 'ls -la'  // Debug: List current directory
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ${IMAGE_NAME} .'
            }
        }

        stage('Run Tests & Copy Results') {
            steps {
                // Use a simpler approach: Run tests in container then extract results
                sh '''
                    # Run tests in container
                    echo "Running tests in container..."
                    docker run --name test-container ${IMAGE_NAME}

                    # Check exit code
                    DOCKER_EXIT=$?
                    echo "Docker exited with code: $DOCKER_EXIT"

                    # Copy all build files from container
                    echo "Copying test results from container..."
                    docker cp test-container:/app/build/. ./build/

                    # Delete container
                    docker rm test-container

                    # Fix permissions
                    chmod -R 755 build/

                    # Verify files were copied
                    echo "Files in build directory after copy:"
                    find build -type f | wc -l
                '''
            }
        }

        stage('Check Test Results') {
            steps {
                // Verify test report exists
                sh '''
                    if [ -f "build/reports/tests/test/index.html" ]; then
                        echo "✅ Test report found!"
                        ls -la build/reports/tests/test/
                    else
                        echo "❌ Test report NOT found!"
                        echo "Directory structure:"
                        find build -type d | sort
                    fi
                '''
            }
        }
    }

    post {
        always {
            // Archive everything in build directory
            archiveArtifacts artifacts: 'build/**/*', allowEmptyArchive: true, fingerprint: true

            // Optionally, print a message to locate report
            echo 'Test artifacts archived. You can download and view index.html from the build artifacts.'
        }
    }
}