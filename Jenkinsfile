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
                // Mount the full build folder so test results are preserved
                sh 'docker run --rm -v $PWD/build:/app/build ${IMAGE_NAME}'
            }
        }
    }

    post {
        always {
            // This path must match where Gradle generates XML reports
            junit 'build/test-results/test/*.xml'
        }
    }
}