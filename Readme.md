# WebScraperApp

Bem-vindo ao **WebScraperApp**, um projeto desenvolvido em Java para realizar a extração de artigos de notícias de um site específico. Este aplicativo utiliza o Selenium WebDriver para interagir com o navegador, o Jsoup para parsear o HTML e extrair os dados desejados, e oferece opções de execução tanto localmente quanto em contêineres Docker.

## Sumário

- [Descrição do Projeto](#descrição-do-projeto)
- [Funcionalidades](#funcionalidades)
- [Pré-requisitos](#pré-requisitos)
- [Configurações](#configurações)
- [Como Executar os Testes](#como-executar-os-testes)
- [Como Executar o Projeto](#como-executar-o-projeto)
    - [Executando Localmente (Sem Docker)](#executando-localmente-sem-docker)
    - [Executando com Docker](#executando-com-docker)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Contribuições](#contribuições)
- [Licença](#licença)

---

## Descrição do Projeto

O **WebScraperApp** é uma aplicação de linha de comando que realiza web scraping em um site de notícias (por padrão, o InfoMoney). Ele navega pelas páginas, extrai informações relevantes dos artigos, como título, subtítulo, autor, data de publicação e conteúdo, e as exibe no console ou as processa conforme necessário.

## Funcionalidades

- **Extração de Artigos de Notícias**: Navega por múltiplas páginas do site e extrai dados dos artigos.
- **Configuração Personalizável**: Permite configurar seletores CSS e URLs através de variáveis de ambiente.
- **Execução Automatizada**: Pode ser executado manualmente ou automatizado em contêineres Docker.
- **Testes Automatizados**: Inclui testes unitários para garantir a qualidade e funcionalidade do código.

## Pré-requisitos

Para executar o projeto localmente, certifique-se de ter instalado:

- **Java JDK 17** ou superior
- **Apache Maven 3.6** ou superior
- **Firefox Browser**
- **GeckoDriver** (compatível com a versão do Firefox instalado)
- **Docker** e **Docker Compose** (para execução com Docker)

## Configurações

O comportamento do aplicativo pode ser personalizado através de variáveis de ambiente:

- `NEWS_SITE_URL`: URL do site de notícias a ser scrapeado. Padrão: `https://www.infomoney.com.br/mercados/`
- `ARTICLE_SELECTOR`: Seletor CSS para os links dos artigos na página inicial.
- `TITLE_SELECTOR`: Seletor CSS para o título do artigo.
- `SUBTITLE_SELECTOR`: Seletor CSS para o subtítulo do artigo.
- `AUTHOR_SELECTOR`: Seletor CSS para o autor do artigo.
- `DATE_SELECTOR`: Seletor CSS para a data de publicação do artigo.
- `CONTENT_SELECTOR`: Seletor CSS para o conteúdo do artigo.
- `BROWSER_DRIVER_PATH`: Caminho para o GeckoDriver.

## Como Executar os Testes

Antes de executar o projeto, é recomendado rodar os testes para garantir que tudo está funcionando corretamente.

### Executando os Testes com Maven

No diretório raiz do projeto, execute:

```bash
mvn clean test
```

Este comando irá compilar o projeto e executar todos os testes definidos na pasta `src/test/java`.

### Executando os Testes em uma IDE

Se preferir utilizar uma IDE como **IntelliJ IDEA** ou **Eclipse**:

- Navegue até a classe de teste que deseja executar.
- Clique com o botão direito e selecione **Run 'NomeDoTeste'**.

## Como Executar o Projeto

### Executando Localmente (Sem Docker)

#### Passo 1: Clonar o Repositório

```bash
git clone https://github.com/joaovneres/WebScraperApp.git
cd WebScraperApp
```

#### Passo 2: Configurar as Variáveis de Ambiente

Configure as variáveis de ambiente conforme necessário. Você pode defini-las no seu sistema ou utilizar um arquivo `.env`.

#### Passo 3: Instalar Dependências

Certifique-se de que o **Firefox** e o **GeckoDriver** estão instalados e configurados no PATH do sistema.

#### Passo 4: Compilar o Projeto

```bash
mvn clean package
```

#### Passo 5: Executar a Aplicação

```bash
java -jar target/WebScraperApp-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### Executando com Docker

#### Passo 1: Clonar o Repositório

```bash
git clone https://github.com/joaovneres/WebScraperApp.git
cd WebScraperApp
```

#### Passo 2: Configurar as Variáveis de Ambiente

Edite o arquivo `docker-compose.yml` ou defina as variáveis de ambiente necessárias.

#### Passo 3: Construir a Imagem Docker

```bash
docker-compose build
```

#### Passo 4: Executar o Contêiner

```bash
docker-compose up
```

Isso irá iniciar o contêiner, executar o scraper e exibir a saída no console.

#### Passo 5: Parar e Remover os Contêineres

Após a execução, você pode parar e remover os contêineres com:

```bash
docker-compose down
```

## Tecnologias Utilizadas

- **Java 17**
- **Selenium WebDriver**: Automação de navegadores.
- **Jsoup**: Parse e manipulação de HTML.
- **JUnit 5**: Framework de testes unitários.
- **Mockito**: Framework para criação de mocks em testes.
- **Maven**: Gerenciamento de dependências e build.
- **Docker**: Contêineres para empacotamento e distribuição.
- **Docker Compose**: Orquestração de contêineres Docker.

## Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.