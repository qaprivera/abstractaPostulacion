pipeline {
    agent any

    environment {
        GRADLE_OPTS = "-Dorg.gradle.jvmargs=-Xmx1024m"
    }

    stages {
        stage('Clonar repositorio') {
            steps {
                git url: 'https://github.com/qaprivera/abstractaPostulacion.git', branch: 'main'
            }
        }

        stage('Compilar y ejecutar tests') {
            steps {
                bat 'gradlew.bat clean test'
            }
        }

        stage('Publicar resultados') {
            steps {
                junit '**/build/test-results/test/TEST-*.xml'
                publishHTML([
                    reportDir: 'build/reports/tests/test',
                    reportFiles: 'index.html',
                    reportName: 'TestNG Reporte HTML'
                ])
            }
        }
    }

    post {
        always {
            echo 'Pipeline finalizado.'
        }
        success {
            echo '¡Éxito! Todos los tests pasaron.'
        }
        failure {
            echo 'Fallaron pruebas. Revisa los reportes.'
        }
    }
}
