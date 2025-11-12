pipeline {
    agent any

    environment {
       PATH = "${env.PATH}:/opt/homebrew/bin"
    }

    stages {
        //Parar todos los servicios
        stage('Parando los servicios'){
            steps {
                sh '''
                  docker compose -p SGU-AAO-10C down || true
                '''
            }
        }
        //eliminar las imagenes anteriores
        stage('Eliminando imagenes antiguas'){
            steps {
                sh '''
                   IMAGES=$(docker images --filter "label=com.docker.compose.project=SGU-AAO-10C" -q)
                   if [-n '$IMAGES' ]; then
                      docker images rmi $IMAGES
                   else
                      echo "No hay imagenes para eliminar"
                   fi
                '''
            }
        }
        //Bajar la actualizacion
        stage('Descargando la actualizacion'){
            steps {
                checkout scm
            }
        }
        //Levantar y desplegar el proyecto
        stage('Desplegando la actualizacion'){
            steps {
                sh '''
                   docker compose -p SGU-AAO-10C up -d --build
                '''
            }
        }
    }

    post{
      success {
        echo 'Despliegue realizado con exito'
      }
      failure {
        echo 'El despliegue ha fallado'
      }
      always {
        echo 'Proceso de despliegue finalizado'
      }
    }
}


