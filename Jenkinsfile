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
            // List files to debug before JUnit collection
            sh '''
                echo "Listing contents of build/test-results:"
                ls -R build/test-results || echo "No test-results found"
            '''

            // Publish test results
            junit 'build/test-results/test/*.xml'
        }
    }
}