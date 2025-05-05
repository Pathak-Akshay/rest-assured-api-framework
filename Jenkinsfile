pipeline {
    agent {
        label 'windows-docker-agent'
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
                // Clean build directory before starting (using Windows commands)
                bat 'if exist build rmdir /s /q build'
                bat 'mkdir build'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t %IMAGE_NAME% .'
            }
        }

        stage('Run Tests') {
            steps {
                // Use Windows syntax for Docker but handle Linux paths correctly inside container
                bat '''
                    docker run --rm -v "%CD%/build:/app/build" %IMAGE_NAME%
                '''
            }
        }

        stage('Check Test Results') {
            steps {
                // Debug step to verify files exist (using Windows commands)
                bat 'dir build'
                bat 'dir build\\reports\\tests\\test || echo "Test reports directory not found"'
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