pipeline{
    agent any
    stages {
        stage('Build Backend'){
            steps{
                sh 'mvn clean package -DskipTests=true'
            }
        }
        stage('Unit Tests'){
            steps{
                sh 'mvn test'
            }
        }
        stage('Sonar Analysis'){
            environment {
                scannerHome = tool 'SONAR_SCANNER'
            }
            steps{
                withSonarQubeEnv('SONAR_LOCAL') {
                    sh "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=2af1628105d17420b61a4cf5baf77ec1d6186de9 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**Application.java,**RootController.java"
                }
            }
        }
        stage('Quality Gate'){
            steps{
                sleep(42)
                timeout(time: 1, unit:'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}

