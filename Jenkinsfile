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

        
}