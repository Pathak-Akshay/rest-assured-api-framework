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

        stage('Build Docker Image') {
            steps {
                script {
                    // Clean up previous build directory
                    bat 'if exist build rmdir /s /q build'
                    bat 'docker build -t %IMAGE_NAME% .'
                }
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    // Create build directory and run with full path
                    bat 'mkdir build'
                    bat 'docker run --rm -v "%cd%\\build:/app/build" %IMAGE_NAME%'
                }
            }
        }
    }

    post {
        always {
            script {
                // Enhanced debugging output
                bat '''
                    echo "Full directory structure:"
                    tree /F build || echo "Tree command not available"

                    echo "Test results specifically:"
                    dir /s build\\test-results || echo "No test-results found"
                '''

                // Publish test results with more flexible pattern
                junit 'build/**/test-results/**/*.xml'
            }

            // Optional: Archive HTML reports
            archiveArtifacts artifacts: 'build/**/reports/tests/**/*', allowEmptyArchive: true
        }
    }
}