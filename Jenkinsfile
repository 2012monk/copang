pipeline {
    agent any

    environment {
        DOCKER_HOST='3.37.2.79:2375'
        APP_NAME='deploy-test'
    }
    stages {

        stage('Prepare') {
            steps {
                check scm
            }
        }

        stage('Build Gradle') {
            agent{
                docker {
                    image 'adoptopenjdk:8-jre-alpine'
                    args '-v $HOME/.gradle:/root/.gradle'
                }
            }

            steps {
                sh './gradlew build '
            }

            post {
                success {
                    echo 'build success'
                }

                failure {
                    echo 'build failed'
                }
            }
        }

        stage('deploy docker image') {
            steps{
                sh "DOCKER_HOST=${DOCKER_HOST} docker-compose -p ${APP_NAME} -f docker-compose up -d"
            }
        }
    }
}