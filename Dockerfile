# Use a imagem base do Maven para construir o projeto
FROM maven:3.8.4-openjdk-11 AS build

# Defina o diretório de trabalho
WORKDIR /app

# Copie o pom.xml e o código-fonte para o diretório de trabalho
COPY pom.xml .
COPY src ./src

# Execute o Maven para construir o projeto
RUN mvn clean package -DskipTests

# Use a imagem base do OpenJDK para a execução
FROM openjdk:11-jre-slim

# Defina o diretório de trabalho
WORKDIR /app

# Copie o JAR construído do estágio de construção para o estágio de execução
COPY --from=build /app/target/reembolso-0.0.1-SNAPSHOT.jar reembolso.jar

# Defina a porta em que a aplicação será executada
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "reembolso.jar"]
