pipeline {
    agent any

    stages {
        stage('Checkout SCM y tests') {
            steps {
                git url: 'https://github.com/qaprivera/abstractaPostulacion.git', branch: 'main'
                
                dir('SelAbstractaPostulacion') {
                    bat '.\\gradlew.bat clean test'
                }
            }
        }

        stage('Publicar reportes') {
            steps {
                dir('SelAbstractaPostulacion') {
                    junit 'build/test-results/test/*.xml'
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finalizado.'
        }
        failure {
            echo 'Fallaron pruebas o no se pudo ejecutar gradlew.bat. Verifica la ubicación.'
        }
        success {
            echo 'Tests ejecutados con éxito desde SelAbstractaPostulacion.'
        }
    }
}
