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
                    reuseNode true
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


            }
        }

        stage('docker vpc test') {
            environment {
                DOCKER_HOST='172.31.44.254:2375'
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
            agent any
            environment {
                DOCKER_HOST='172.31.44.254:2375'
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