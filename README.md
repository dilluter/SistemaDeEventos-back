Backend Sistema de Eventos
API REST desenvolvida em Java com Spring Boot para gerenciamento de eventos, incluindo cadastro, consulta e regras de negócio específicas para datas e endereços.

Tecnologias
Java 21

Spring Boot 3 (Web, Data JPA)
​

PostgreSQL como banco de dados principal
​

Flyway para migrações de banco
​

AWS S3 para armazenamento de arquivos/imagens
​

Lombok para redução de boilerplate

DevTools para live reload em desenvolvimento

JUnit, Mockito e AssertJ para testes automatizados
​

Pré‑requisitos
Java 21 instalado e configurado (JAVA_HOME)
​

Maven 3.6+ instalado
​

PostgreSQL em execução com um banco criado (ex.: eventos_db)
​

Conta AWS com credenciais válidas e um bucket S3 configurado
​

Configuração
Crie um arquivo application.properties ou application.yml com:

URL do banco PostgreSQL (spring.datasource.url, username, password)
​

Configurações do Flyway (spring.flyway.enabled=true e pasta de migrations classpath:db/migration)
​

Credenciais da AWS (cloud.aws.credentials.access-key, secret-key, região e nome do bucket S3)
​

As migrações do Flyway serão executadas automaticamente na inicialização da aplicação, garantindo que o schema do banco esteja sempre versionado e atualizado.
​

Execução do projeto
Para rodar a aplicação em modo desenvolvimento:

bash
mvn spring-boot:run
Este comando sobe o servidor embutido (Tomcat) na porta padrão 8080, carregando as entidades JPA, executando as migrations Flyway e conectando ao PostgreSQL configurado.
​

Testes
Para executar a suíte de testes unitários e de integração:

bash
mvn test
Os testes utilizam JUnit, Mockito e AssertJ para validação de regras de negócio, serviços e repositórios.
​

Mocks são criados com Mockito para isolar dependências externas (repositórios, serviços de S3, etc.).
​

Estrutura geral (sugestão)
domain/ entidades de domínio (Event, Address, Coupon, etc.)
​
​

repository/ interfaces JPA para acesso ao PostgreSQL

service/ regras de negócio e integração com AWS S3

controller/ endpoints REST de eventos, endereços e cupons

db/migration/ scripts SQL de versão do banco gerenciados pelo Flyway
​

Sinta-se à vontade para pedir uma versão em inglês ou incluir a lista de endpoints que já existem na API.
