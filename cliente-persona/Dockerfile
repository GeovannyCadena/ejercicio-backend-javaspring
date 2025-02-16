# Etapa 1 Compilación
FROM gradle:8.10-jdk17 AS build

WORKDIR app
COPY . .

# Compilar y generar el JAR
RUN gradle clean build -x test

# Etapa 2 Imagen final
FROM eclipse-temurin:17-jdk-jammy

WORKDIR app
COPY --from=build /app/build/libs/*.jar app.jar

# Puerto de la aplicación
EXPOSE 8081

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
