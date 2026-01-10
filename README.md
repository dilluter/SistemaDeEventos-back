# 🎉 Backend – Sistema de Eventos

API REST desenvolvida em **Java** com **Spring Boot** para gerenciamento de eventos, incluindo cadastro, consulta e regras de negócio relacionadas a datas, endereços e cupons. Possui integração com **AWS S3** para armazenamento de arquivos e imagens.

---

## 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot 3
  - Spring Web
  - Spring Data JPA
- PostgreSQL
- Flyway
- AWS S3
- Lombok
- Spring Boot DevTools
- JUnit 5, Mockito e AssertJ

---

## 📋 Pré-requisitos

- Java 21 (JAVA_HOME configurado)
- Maven 3.6+
- PostgreSQL em execução
- Banco de dados criado (ex: `eventos_db`)
- Conta AWS com credenciais válidas e bucket S3 configurado

---

## ⚙️ Configuração

Crie o arquivo `application.properties` em `src/main/resources`.

### Banco de Dados

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/eventos_db
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration


## 🗂️ Estrutura do Projeto

```text
src/
 └── main/
     ├── java/
     │   └── com/eventos/back/
     │       ├── controller/
     │       ├── service/
     │       ├── repository/
     │       └── domain/
     └── resources/
         ├── application.properties
         └── db/
             └── migration/
📌 Observações

O projeto segue boas práticas de arquitetura em camadas

Pronto para expansão com:

Autenticação (JWT / OAuth2)

Documentação com Swagger / OpenAPI

Deploy em AWS (EC2, ECS ou Elastic Beanstalk)
