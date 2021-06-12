pipeline {
    agent any


    stages {
        stage('Prepare') {
            steps {
                cleanWs()
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
                sh './gradlew clean build '
            }

            post {
                success {
                    echo 'build success'
                }

                failure {
                    echo 'build failed'
                }

                always {
                    cleanWs()
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
            environment {
                DOCKER_HOST='3.37.2.79:2375'
                APP_NAME='deploy-test'
            }
            steps{
                sh "ls -al"
                sh "DOCKER_HOST=${DOCKER_HOST} docker-compose -f docker-compose.yml rm -f"
                sh "DOCKER_HOST=${DOCKER_HOST} docker-compose -p ${APP_NAME} -f docker-compose.yml pull "
                sh "DOCKER_HOST=${DOCKER_HOST} docker-compose -p ${APP_NAME} -f docker-compose.yml up -d"
            }

            post {
                success {
                    echo 'sueccess deployment'
                }
            }
        }
    }
}