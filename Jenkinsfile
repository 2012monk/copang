pipeline {
    agent any


    stages {
        stage('Prepare') {
            steps {
                checkout scm
            }
        }

        stage('Build Gradle') {
            agent{
                docker {
                    image 'openjdk:8-jdk-alpine'
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

        stage('docker test') {
            environment {
                DOCKER_HOST='3.37.2.79:2375'
                APP_NAME='deploy-test'
            }
            steps {
                sh 'docker -H ${DOCKER_HOST} images'
            }

            post {
                failure {
                    sh 'connection failed'
                }
            }
        }

        stage('deploy docker image') {
            steps{
                sh "DOCKER_HOST=${DOCKER_HOST} docker-compose -p ${APP_NAME} -f docker-compose up -d"
            }

            post {
                success {
                    echo 'sueccess deployment'
                }
            }
        }
    }
}