# banco-pan-challenge

Atualmente não é possível que o cliente realize a alteração de 
dados cadastrais através dos canais online. Exemplo: Endereço.  
Apenas através da central de atendimento é possível solicitar a 
alteração deste dado cadastral. Com o objetivo de reduzir custos de 
Call Center e proporcionar comodidade e agilidade para os clientes, 
precisamos possibilitar a alteração dos dados cadastrais através dos 
nossos canais digitais.

# Bibliotecas utilizadas

 - Spring Boot
 - H2 Database (Banco de dados em memória)
 - Logstash (Logs estruturados)
 - Junit (Testes unitários)
 - Mockito (Mock para auxiliar o teste unitário

# Endpoints

## Consulta cliente por CPF

    curl --location 'http://localhost:8080/clientes/12345678901' \
    --header 'Accept: application/json'

## Alterar endereço do cliente

    curl --location --request PUT 'http://localhost:8080/clientes/12345678901/endereco' \
    --header 'Content-Type: application/json' \
    --data '{
        "zipCode": "08547890",
        "street": "Rua Nova York",
        "number": "707",
        "complement": "Apto 45",
        "neighborhood": "Centro",
        "city": "São Paulo",
        "state": "SP"
    }'

## Consulta endereço por CEP

    curl --location 'http://localhost:8080/enderecos/08673115' \
    --header 'Accept: application/json'

## Consulta estados

    curl --location 'http://localhost:8080/enderecos/estados' \
    --header 'Accept: application/json'

## Consulta municípios por código do estado

    curl --location 'http://localhost:8080/enderecos/estados/35/municipios' \
    --header 'Accept: application/json'

# Instrução para compilação do projeto

Antes de tudo certifique-se de que tem instalado tanto o **Java** na versão **23** e o **Maven**
Maven: [Download Apache Maven – Maven](https://maven.apache.org/download.cgi)
Java: [Java Downloads | Oracle](https://www.oracle.com/java/technologies/downloads/)

Primeiro passo descompactar o arquivo zip recebido e navegar até a pasta onde foi descompactado o arquivo do projeto, utilizando o terminal do seu sistema operacional.

## Comandos para compilar 

1º Comando

    mvn clean install

Execute este comando para garantir que todas as dependências sejam baixadas.

2º Comando


    mvn clean package

-   **`clean`**: Remove arquivos antigos gerados.
-   **`package`**: Compila o código e empacota o JAR.

O Maven criará o arquivo JAR na pasta `target` 


## Instrução para executar 

Para executar o arquivo .jar gerado anteriormente navegue para a pasta target e execute o comando abaixo. 

    java -jar target/banco-pan-challenge-1.0-SNAPSHOT.jar

Após este comando o prompt estará disponível para receber a entrada em formato JSON das operações.

## Comando para executar os testes unitários
Na pasta do projeto que foi descompactada executar o comando abaixo

    mvn test

-   Localiza todos os testes na pasta `src/test/java`.
-   Executa os testes usando o framework de teste configurado (neste caso, `JUnit` e `Mockito`).
-   Gera um relatório de teste na pasta `target/surefire-reports`.
