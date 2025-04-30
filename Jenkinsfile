pipeline {

   agent {
      docker {
         image 'docker:24.0.5-cli'
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
            script {
               sh "docker build -t ${IMAGE_NAME} ."
            }
         }
      }

      stage('Run Tests') {
         steps {
            script {
               sh "docker run --rm ${IMAGE_NAME}"
            }
         }
      }
   }

   post {
      always {
         junit '**/build/test-results/test/*.xml'
      }
   }
}