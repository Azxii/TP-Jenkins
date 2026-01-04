pipeline {
    agent any

    stages {
        stage('Test') {
            steps {
                // 1. Lancement des tests unitaires
                sh './gradlew test'
            }
            post {
                always {
                    // 2. Archivage des résultats des tests unitaires
                    junit 'build/test-results/test/*.xml'

                    // 3. Génération des rapports de tests Cucumber
                    cucumber 'build/reports/cucumber/*.json'
                }
            }
        }

        stage('Code Analysis') {
            steps {
                withSonarQubeEnv('sonar') {
                    sh './gradlew sonarqube'
                }
            }
        }

        stage('Code Quality') {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Build') {
            steps {
                // 1. Génération du fichier Jar
                sh './gradlew assemble'

                // 2. Génération de la documentation
                sh './gradlew javadoc'
            }
            post {
                success {
                    // 3. Archivage du fichier Jar et de la documentation
                    archiveArtifacts artifacts: 'build/libs/*.jar, build/docs/javadoc/**'
                }
            }
        }

        stage('Deploy') {
            steps {
                // Déployer le fichier Jar dans mymavenrepo.com
                sh './gradlew publish'
            }
        }
    }

    post {
        success {
            // Notification par mail
            mail to: 'h_mokeddem@esi.dz',
                 subject: "Success: ${currentBuild.fullDisplayName}",
                 body: "The build and deploy were successful."

            // Notification sur Slack
            slackSend channel: '#tp_ogl_gradle', message: "Build Success: ${currentBuild.fullDisplayName}"
        }
        failure {
            // En cas d'échec
            mail to: 'h_mokeddem@esi.dz',
                 subject: "Failed: ${currentBuild.fullDisplayName}",
                 body: "The pipeline failed in stage: ${env.STAGE_NAME}"

            slackSend channel: '#tp_ogl_gradle', color: 'danger', message: "Build Failed: ${currentBuild.fullDisplayName}"
        }
    }
}