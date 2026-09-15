# TaskFlow

Gerenciador de tarefas de equipe — projeto full-stack para praticar Java/Spring Boot no back-end e React no front-end, do modelo de domínio até um ambiente dockerizado.

## Stack

- **Java 21** (Eclipse Temurin)
- **Spring Boot 3.4.1** (Web, Data JPA, Validation)
- **PostgreSQL 16**
- **Flyway** (migrations versionadas)
- **Lombok**
- **Maven**
- **Docker** (Dockerfile multi-stage + docker-compose)
- JUnit 5 + AssertJ

## Domínio

Três entidades com relacionamento entre elas:

- **User** — usuário do sistema, pode ser responsável (`assignee`) por tarefas.
- **Project** — projeto de equipe, agrupa tarefas (`1:N` com `Task`).
- **Task** — tarefa associada a um `Project` e a um `User` responsável, com `status` (`PENDING`, `IN_PROGRESS`, `DONE`).

## Estrutura do projeto

```
com.webapps.taskflow
├── entity/      → User, Project, Task, Status (enum) — mapeamento JPA + Lombok
├── repository/  → Spring Data JPA repositories
├── dtos/        → CreateRequest/UpdateRequest/Response records por entidade, com Bean Validation
├── mapper/      → conversão entidade ↔ DTO
└── controller/  → REST controllers
```

A API não expõe as entidades JPA diretamente — todo `GET`/`POST`/`PUT`/`DELETE` passa por DTOs (`record` do Java), desacoplando o contrato da API do modelo de persistência.

## Endpoints atuais

Nos 3 recursos (`/users`, `/projects`, `/tasks`):

| Método | Rota        | Descrição                                                   |
|--------|-------------|---------------------------------------------------------------|
| GET    | `/{recurso}`      | Lista paginada (`?page=&size=&sort=`)                    |
| POST   | `/{recurso}`      | Cria (Bean Validation, `404` se referência não existir)  |
| PUT    | `/{recurso}/{id}` | Atualiza (Bean Validation, `404` se não existir)         |
| DELETE | `/{recurso}/{id}` | Soft delete (`404` se já excluído/inexistente)           |

`DELETE /projects/{id}` cascateia soft delete pras tasks do projeto.

## Rodando localmente

### Via Docker (recomendado)

```
docker compose up --build
```

Sobe a API (`localhost:8080`) e o Postgres juntos; o Flyway aplica as migrations automaticamente.

### Direto na IDE

1. PostgreSQL rodando localmente, banco `taskflow` criado.
2. Ajustar `TaskFlow/src/main/resources/application.properties` com usuário/senha do banco, se necessário.
3. Rodar `TaskFlowApplication` (Java 21, precisa do plugin Lombok + annotation processing habilitados na IDE) — sobe em `http://localhost:8080`.

## Status / próximos passos

- [x] Modelagem do domínio + persistência via JPA/Hibernate
- [x] API REST (`GET`/`POST`/`PUT`/`DELETE`) com DTOs e mappers
- [x] Bean Validation
- [x] Paginação nas listagens
- [x] Soft delete (com cascata Project → Task)
- [x] Migrations versionadas com Flyway
- [x] Testes unitários (entidades e mappers)
- [x] Dockerfile + docker-compose (app + banco)
- [ ] Front-end React + TypeScript consumindo a API
