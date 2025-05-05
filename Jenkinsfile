pipeline {
    agent {
        label 'windows-docker-agent'  // This should actually be a Linux agent based on the error
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
                // Clean build directory before starting (using Linux commands)
                sh 'rm -rf build || true'
                sh 'mkdir -p build'
                sh 'chmod -R 777 build'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ${IMAGE_NAME} .'
            }
        }

        stage('Run Tests') {
            steps {
                // Use Linux syntax for Docker commands
                sh '''
                    docker run --rm -v "$(pwd)/build:/app/build" ${IMAGE_NAME}
                '''
            }
        }

        stage('Check Test Results') {
            steps {
                // Debug step to verify files exist (using Linux commands)
                sh 'ls -la build'
                sh 'ls -la build/reports/tests/test || echo "Test reports directory not found"'
            }
        }
    }

    post {
        always {
            // Archive HTML test report
            archiveArtifacts artifacts: 'build/reports/tests/test/**', allowEmptyArchive: true, fingerprint: true

            // Also archive raw test results if needed
            archiveArtifacts artifacts: 'build/test-results/test/**', allowEmptyArchive: true, fingerprint: true

            // Optionally, print a message to locate report
            echo 'HTML report archived. You can download and view index.html from the build artifacts.'
        }
    }
}