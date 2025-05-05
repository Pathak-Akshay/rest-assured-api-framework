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
                sh 'docker build -t ${IMAGE_NAME} .'
            }
        }

        stage('Run Tests') {
            steps {
                // Mount full build directory to persist test results
                sh '''
                    mkdir -p build
                    docker run --rm -v "$PWD/build:/app/build" ${IMAGE_NAME}
                '''
            }
        }
    }

    post {
        always {
                    // Archive HTML test report
                    archiveArtifacts artifacts: 'build/reports/tests/test/**', fingerprint: true

                    // Optionally, print a message to locate report
                    echo 'HTML report archived. You can download and view index.html from the build artifacts.'
                }
    }
}