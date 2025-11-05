# Usa imagen base de Java 17 (compatible con Spring Boot)
FROM eclipse-temurin:17-jdk-alpine

# Define el directorio de trabajo
WORKDIR /app

# Copia el archivo pom.xml y descarga dependencias
COPY pom.xml .
RUN ./mvnw dependency:go-offline -B

# Copia el resto del código
COPY . .

# Compila y genera el jar
RUN ./mvnw package -DskipTests

# Expone el puerto
EXPOSE 8080

# Ejecuta el jar resultante
CMD ["java", "-jar", "target/*.jar"]
