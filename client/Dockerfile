# Fase 1: Build (Construcción de la aplicación React/Vite)
FROM node:20 AS build 

WORKDIR /app

# Copiar archivos de configuración y manifiesto de dependencias
COPY package*.json ./

# Instalar dependencias
# Se usa 'npm install' como en el ejemplo anterior
RUN npm install 

# Copiar el resto del código fuente (incluyendo .env)
COPY . .

# Argumentos de construcción para inyectar variables de entorno de la API
# Esto es crucial para que Vite pueda incrustar la URL base en el código
ARG API_HOST
ARG API_PORT
ARG API_BASE

# Asignar los argumentos a variables de entorno que Vite puede leer
ENV VITE_API_HOST=$API_HOST 
ENV VITE_API_PORT=$API_PORT
ENV VITE_API_BASE=$API_BASE

# Ejecutar el proceso de build de Vite
RUN npm run build 

# Fase 2: Producción (Servir con Nginx)
FROM nginx:alpine

# Copiar los archivos estáticos de la construcción a la carpeta de servicio de Nginx
COPY --from=build /app/dist /usr/share/nginx/html

# Exponer el puerto 80, donde Nginx estará escuchando
EXPOSE 80

# Comando para iniciar Nginx
CMD ["nginx", "-g", "daemon off;"]