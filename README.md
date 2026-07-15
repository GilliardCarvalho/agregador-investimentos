# Agregador de Investimentos

API REST em Java/Spring Boot para gerenciar usuários, contas de investimento e ações, com consulta de cotações pela BRAPI.

## Tecnologias

- Java 21 e Spring Boot
- Spring Data JPA e MySQL
- OpenFeign
- Docker Compose

## Como executar

1. Copie `.env.example` para `.env` e substitua as senhas de exemplo.
2. Inicie o banco de dados:

   ```bash
   docker compose up -d
   ```

3. Inicie a API:

   ```bash
   ./mvnw spring-boot:run
   ```

No Windows, use `mvnw.cmd spring-boot:run`. A API fica disponível em `http://localhost:8081`.

## Rotas principais

| Método | Rota | Descrição |
| --- | --- | --- |
| POST | `/v1/users` | Cria um usuário |
| GET | `/v1/users` | Lista usuários |
| GET, PUT, DELETE | `/v1/users/{userId}` | Consulta, atualiza ou remove um usuário |
| POST, GET | `/v1/users/{userId}/accounts` | Cria ou lista contas do usuário |
| POST, GET | `/v1/accounts/{accountId}/stocks` | Associa ou lista ações da conta |
| POST | `/v1/stocks` | Cadastra uma ação |

## Configuração

As credenciais ficam no arquivo local `.env`, que não é enviado ao Git. Use `.env.example` como modelo. A aplicação lê `MYSQL_HOST`, `MYSQL_PORT`, `MYSQL_DATABASE`, `MYSQL_USER` e `MYSQL_PASSWORD`.
