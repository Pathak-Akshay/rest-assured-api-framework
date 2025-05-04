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
                sh '''
                mkdir -p test-results
                docker run --rm -v $PWD/build/test-results/test:/app/build/test-results/test \
                 -e TEST_RESULTS_DIR=/app/build/test-results/test \
                 ${IMAGE_NAME}
                 '''
            }
        }
    }

    post {
        always {
            // Make sure this path exists or Gradle is generating XML test reports
            junit 'test-results/test/*.xml'
        }
    }
}