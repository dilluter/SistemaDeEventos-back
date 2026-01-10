Backend – Sistema de Eventos

API REST desenvolvida em Java com Spring Boot para gerenciamento de eventos, incluindo cadastro, consulta e aplicação de regras de negócio relacionadas a datas, endereços e cupons, além de integração com AWS S3 para armazenamento de arquivos/imagens.

Tecnologias Utilizadas

Java 21

Spring Boot 3

Spring Web

Spring Data JPA

PostgreSQL (banco de dados relacional)

Flyway (versionamento e migração de banco)

AWS S3 (armazenamento de arquivos/imagens)

Lombok (redução de boilerplate)

Spring Boot DevTools (live reload em desenvolvimento)

JUnit 5, Mockito e AssertJ (testes automatizados)

Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

Java 21 (JAVA_HOME configurado)

Maven 3.6+

PostgreSQL em execução, com um banco criado
Exemplo: eventos_db

Conta AWS com:

Access Key

Secret Key

Bucket S3 configurado

Configuração do Projeto

Crie um arquivo application.properties ou application.yml em src/main/resources e configure conforme abaixo.

Banco de Dados (PostgreSQL)
spring.datasource.url=jdbc:postgresql://localhost:5432/eventos_db
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration


As migrações do Flyway são executadas automaticamente na inicialização da aplicação, garantindo que o schema do banco esteja sempre versionado e atualizado.

AWS S3
cloud.aws.credentials.access-key=SUA_ACCESS_KEY
cloud.aws.credentials.secret-key=SUA_SECRET_KEY
cloud.aws.region.static=us-east-1

app.aws.s3.bucket=seu-bucket-eventos

 Executando o Projeto

Para subir a aplicação localmente, execute:

mvn spring-boot:run


A aplicação será iniciada em:

http://localhost:8081


Durante a inicialização:

O Spring Boot conecta ao PostgreSQL

As migrações do Flyway são aplicadas automaticamente

Executando os Testes
mvn test


Os testes utilizam:

JUnit 5

Mockito para mock de dependências

AssertJ para validações fluentes

As integrações externas, como repositórios JPA e AWS S3, são devidamente mockadas para garantir testes isolados e confiáveis.

Estrutura do Projeto
src/
 └── main/
     ├── java/
     │   └── com/eventos/back/
     │       ├── controller/    # Endpoints REST (Eventos, Endereços, Cupons)
     │       ├── service/       # Regras de negócio e integração com S3
     │       ├── repository/    # Interfaces JPA (PostgreSQL)
     │       └── domain/        # Entidades (Event, Address, Coupon, etc.)
     └── resources/
         ├── application.yml   # Configurações da aplicação
         └── db/
             └── migration/    # Scripts Flyway (V1__*.sql, V2__*.sql, ...)

Observações

O projeto segue boas práticas de arquitetura em camadas

Pronto para expansão com:

Autenticação (JWT / OAuth2)

Documentação com Swagger / OpenAPI

Deploy em AWS (EC2, ECS ou Elastic Beanstalk)
