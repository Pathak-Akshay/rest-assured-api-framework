pipeline {
    agent {
        docker {
            image 'gradle:8.5-jdk17'  // You can change this to your preferred JDK + Gradle combo
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
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
                sh 'docker run --rm ${IMAGE_NAME}'
            }
        }
    }

    post {
        always {
            // Make sure this path exists or Gradle is generating XML test reports
            junit '**/build/test-results/test/*.xml'
        }
    }
}