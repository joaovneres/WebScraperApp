# Etapa 1: Construir e rodar testes usando Maven
FROM maven:3.8.1-openjdk-17 AS build

# Copia o projeto para o container
COPY . /app
WORKDIR /app

# Roda os testes e constrói o JAR com dependências
RUN mvn clean package

# Etapa 2: Configurar o container para executar o JAR final
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR gerado da etapa de build
COPY --from=build /app/target/WebScraperApp-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar

# Instala o Firefox, GeckoDriver e dependências necessárias
RUN apt-get update && \
    apt-get install -y wget unzip curl firefox-esr && \
    wget -O /tmp/geckodriver.tar.gz https://github.com/mozilla/geckodriver/releases/download/v0.33.0/geckodriver-v0.33.0-linux64.tar.gz && \
    tar -xzf /tmp/geckodriver.tar.gz -C /usr/local/bin/ && \
    rm /tmp/geckodriver.tar.gz && \
    chmod +x /usr/local/bin/geckodriver && \
    rm -rf /var/lib/apt/lists/*

# Define o GeckoDriver e o Firefox na variável PATH
ENV PATH="/usr/local/bin/geckodriver:/usr/bin/firefox:${PATH}"

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
