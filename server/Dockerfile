# Se usa la imagen de Maven con Java 21 (Temurin) para construir
FROM maven:3.9.9-eclipse-temurin-21

WORKDIR /app 

# Copiar todo el código fuente del servidor
COPY . .

# Empaquetar la aplicación Spring Boot. 
# Se salta la ejecución de tests para acelerar el build de Docker.
RUN mvn clean package -DskipTests

# Renombrar el JAR resultante para un nombre consistente
RUN cp target/*.jar app.jar

# Exponer el puerto 8080 que usará Spring Boot internamente
EXPOSE 8080

# Definir el comando de entrada para ejecutar la aplicación
ENTRYPOINT ["java","-jar","app.jar"]