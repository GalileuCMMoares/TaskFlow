# TaskFlow

Gerenciador de tarefas de equipe — projeto full-stack para praticar Java/Spring Boot no back-end e React no front-end, do modelo de domínio até um ambiente dockerizado.

## Stack

- **Java 21** (Eclipse Temurin)
- **Spring Boot 3.4.1** (Web, Data JPA, Validation)
- **PostgreSQL 16**
- **Maven**

## Domínio

Três entidades com relacionamento entre elas:

- **User** — usuário do sistema, pode ser responsável (`assignee`) por tarefas.
- **Project** — projeto de equipe, agrupa tarefas (`1:N` com `Task`).
- **Task** — tarefa associada a um `Project` e a um `User` responsável, com `status` (`PENDING`, `IN_PROGRESS`, `DONE`).

## Estrutura do projeto

```
com.webapps.taskflow
├── entity/      → User, Project, Task, Status (enum) — mapeamento JPA
├── repository/  → Spring Data JPA repositories
├── dtos/        → Request/Response records por entidade (contrato da API)
├── mapper/      → conversão entidade ↔ DTO
└── controller/  → REST controllers
```

A API não expõe as entidades JPA diretamente — todo `GET`/`POST` passa por DTOs (`record` do Java), desacoplando o contrato da API do modelo de persistência.

## Endpoints atuais

| Método | Rota        | Descrição                              |
|--------|-------------|-----------------------------------------|
| GET    | `/users`    | Lista usuários                          |
| POST   | `/users`    | Cria usuário                            |
| GET    | `/projects` | Lista projetos                          |
| POST   | `/projects` | Cria projeto                            |
| GET    | `/tasks`    | Lista tarefas                           |
| POST   | `/tasks`    | Cria tarefa (referencia `assigneeId`/`projectId` já existentes) |

Requisições de criação passam por Bean Validation (`@Valid`); referência a `User`/`Project` inexistente retorna `404`.

## Rodando localmente

1. PostgreSQL rodando localmente, banco `taskflow` criado.
2. Ajustar `TaskFlow/src/main/resources/application.properties` com usuário/senha do banco, se necessário.
3. Rodar `TaskFlowApplication` (Java 21) — sobe em `http://localhost:8080`.

## Status / próximos passos

- [x] Modelagem do domínio + persistência via JPA/Hibernate
- [x] API REST (`GET`/`POST`) com DTOs e mappers
- [x] Bean Validation nas requisições de criação
- [ ] Paginação nas listagens
- [ ] Soft delete
- [ ] Dockerfile + docker-compose (app + banco)
- [ ] Front-end React + TypeScript consumindo a API
