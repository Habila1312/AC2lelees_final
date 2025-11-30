pipeline {
    agent any

    environment {
        IMAGE_NAME = "habila/ac2lelees_final:latest"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Tests (Quality Gate 99%)') {
            steps {
                bat 'mvn clean verify -DskipTests=false'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                    jacoco execPattern: 'target/jacoco.exec'
                    pmd canRunOnFailed: true, pattern: 'target/pmd.xml'
                }
                success {
                    script {
                        // Obtém a cobertura da linha do Jacoco para validação
                        def coverage = jacoco(
                            execPattern: 'target/jacoco.exec'
                        )
                        if (coverage.lineCoverage < 0.99) {
                            error "❌ Quality Gate NÃO atingido! Cobertura atual: ${coverage.lineCoverage * 100}%"
                        }
                    }
                }
            }
        }

        stage('Build Docker Image') {
            when {
                expression {
                    // Apenas se o stage anterior passou com sucesso
                    currentBuild.result == null || currentBuild.result == 'SUCCESS'
                }
            }
            steps {
                bat "docker build -t ${env.IMAGE_NAME} ."
                bat "docker push ${env.IMAGE_NAME}"
            }
        }

        stage('Deploy Staging') {
            steps {
                bat "docker compose down || echo 'Sem container para derrubar!'"
                bat "docker compose up -d"
                sleep time: 30, unit: 'SECONDS'
            }
        }
    }

    post {
        always {
            echo "Pipeline Finalizada"
        }
    }
}
