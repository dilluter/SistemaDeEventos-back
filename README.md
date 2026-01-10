# Backend Sistema de Eventos

API REST desenvolvida em **Java** com Spring Boot para gerenciamento de eventos, incluindo cadastro, consulta e regras de negócio específicas para datas e endereços. [web:55]

## Tecnologias

- Java 21  
- Spring Boot 3 (Web, Data JPA)
- PostgreSQL como banco de dados principal 
- Flyway para migrações de banco  
- AWS S3 para armazenamento de arquivos/imagens 
- Lombok para redução de boilerplate  
- Spring Boot DevTools para live reload em desenvolvimento
- JUnit, Mockito e AssertJ para testes automatizados

## Pré‑requisitos

- Java 21 instalado e configurado (JAVA_HOME) 
- Maven 3.6+ instalado
- PostgreSQL em execução com um banco criado (por exemplo, `eventos_db`)
- Conta AWS com credenciais válidas e um bucket S3 configurado 

## Configuração

Crie um arquivo `application.properties` ou `application.yml` com as propriedades abaixo, ajustando usuário, senha, URL e bucket:

spring.datasource.url=jdbc:postgresql://localhost:5432/eventos_db
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration

Configure também as credenciais da AWS para acesso ao S3:

text
cloud.aws.credentials.access-key=SUA_ACCESS_KEY
cloud.aws.credentials.secret-key=SUA_SECRET_KEY
cloud.aws.region.static=us-east-1
app.aws.s3.bucket=seu-bucket-eventos

As migrações do Flyway são executadas automaticamente na inicialização da aplicação, garantindo que o schema do banco esteja versionado e atualizado.

Executando o projeto
bash
mvn spring-boot:run
O comando acima sobe o servidor embutido do Spring Boot na porta padrão 8080, conecta ao PostgreSQL configurado e aplica as migrações do Flyway.

Testes
bash
mvn test
Os testes utilizam JUnit, Mockito e AssertJ para validar regras de negócio, serviços e repositórios de forma isolada, mockando dependências externas como repositórios JPA e integrações com AWS S3.

Estrutura do projeto
text
src/
 └── main/
     ├── java/
     │   └── com/eventos/back/
     │       ├── controller/    # Endpoints REST de eventos, endereços, cupons
     │       ├── service/       # Regras de negócio e integração com S3
     │       ├── repository/    # Interfaces JPA para PostgreSQL
     │       └── domain/        # Entidades (Event, Address, Coupon, etc.)
     └── resources/
         ├── application.yml    # Configurações da aplicação
         └── db/
             └── migration/     # Scripts SQL do Flyway (V1__*.sql, V2__*.sql, ...)
