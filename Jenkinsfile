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
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ${IMAGE_NAME} .'
            }
        }

        stage('Run Tests & Copy Results') {
            steps {
                // Run tests in container then extract results
                sh '''
                    # Run tests in container
                    docker run --name test-container ${IMAGE_NAME}

                    # Copy build files from container
                    docker cp test-container:/app/build/. ./build/

                    # Delete container
                    docker rm test-container

                    # Fix permissions
                    chmod -R 755 build/
                '''
            }
        }
    }

    post {
        always {
            // Archive HTML test report
            archiveArtifacts artifacts: 'build/reports/tests/test/**/*', allowEmptyArchive: true, fingerprint: true

            // Also archive raw test results if available
            archiveArtifacts artifacts: 'build/test-results/test/**/*', allowEmptyArchive: true, fingerprint: true

            echo 'Test artifacts archived. You can view index.html in the build artifacts.'

            // Publish test results
            junit 'build/test-results/test/*.xml'
        }
    }
}