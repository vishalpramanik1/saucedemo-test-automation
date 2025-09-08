pipeline {
    agent any

    tools {
        jdk 'Java17'       // Name you set in Jenkins Global Tool Configuration
        maven 'Maven3'     // Name you set for Maven
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/vishalpramanik1/saucedemo-test-automation.git'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean test -DsuiteXmlFile=testng.xml'
            }
        }

        stage('Archive Reports') {
            steps {
                // Archive ExtentReports + Screenshots + TestNG reports
                archiveArtifacts artifacts: 'reports/**', fingerprint: true
                archiveArtifacts artifacts: 'screenshots/**', allowEmptyArchive: true
                archiveArtifacts artifacts: 'test-output/**', allowEmptyArchive: true
            }
        }

        stage('Publish Cucumber Report') {
            steps {
                cucumber fileIncludePattern: '**/cucumber.json', jsonReportDirectory: 'target'
            }
        }
    }

    post {
        always {
            echo '✅ Pipeline finished! Reports and screenshots are archived.'
        }
    }
}
