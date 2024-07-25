# Meu Reembolso

Este é um projeto de gerenciamento de solicitações de reembolso, que inclui funcionalidades para solicitar, consultar e cancelar reembolsos. O projeto utiliza Spring Boot para o backend e MongoDB como banco de dados.

## Funcionalidades

- Solicitar um reembolso.
- Consultar o status de uma solicitação de reembolso.
- Cancelar uma solicitação de reembolso.
- Geração de link de acompanhamento usando um serviço de mascaramento de URL.

## Requisitos

Antes de começar, certifique-se de ter os seguintes requisitos instalados:

- [Java JDK 18+](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/download.cgi) (ou outro gerenciador de dependências)
- [MongoDB](https://www.mongodb.com/try/download/community)
- [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)

## Configuração

### Configuração do Banco de Dados

Certifique-se de que o MongoDB esteja em execução. Configure a URL do MongoDB no arquivo `application.properties`:

```properties
spring.data.mongodb.uri=mongodb://localhost/meureembolso

##POSTMAN ENDPOINTS
Acessar o arquivo "Reembolso.postman_collection"
Importar no Postman







