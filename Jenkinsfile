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
                            mkdir -p test-results/test
                            docker run --rm \
                              -v $PWD/build/test-results:/app/test-results \
                              -e TEST_RESULTS_DIR=/app/test-results \
                              ${IMAGE_NAME}
                        '''
            }
        }
    }

    post {
        always {
                echo "Listing contents of test-results directory:"
                sh 'ls -la test-results'

                echo "Listing contents of test-results/test directory:"
                sh 'ls -la test-results/test || echo "test directory does not exist"'

                echo "Looking for JUnit XML files:"
                sh 'find test-results -name "*.xml" || echo "No XML files found"'

                junit 'test-results/**/*.xml'
            }
    }
}