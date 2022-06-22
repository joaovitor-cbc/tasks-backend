pipeline{
    agent any
    stages{
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
                sleep(5)
                timeout(time: 1, unit:'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Deploy Backend'){
            steps{
                deploy adapters: [tomcat8(credentialsId: 'login-tomcat', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }
        stage('API Test'){
            steps{
                dir('api-test') {
                    git credentialsId: 'login-github', url: 'https://github.com/joaovitor-cbc/task-api-test'
                    sh 'mvn test'
                }   
            }
        }
        stage('Git clone Frontend'){
            dir('frontend'){
                git credentialsId: 'login-github', url: 'https://github.com/joaovitor-cbc/tasks-frontend'
            }
        }
        stage('Build Frontend'){
            dir('frontend'){
                sh 'mvn clean package'
            }
        }
        stage('Deploy Frontend'){
            dir('frontend'){
                deploy adapters: [tomcat8(credentialsId: 'login-tomcat', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks', war: 'target/tasks.war'
            }
        }       
    }
}

