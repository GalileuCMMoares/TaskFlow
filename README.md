# TaskFlow

Gerenciador de tarefas de equipe no estilo Jira — projeto full-stack Java/Spring Boot + React, do modelo de domínio até autenticação real e deploy.

## Stack

**Back-end**
- **Java 21** (Eclipse Temurin)
- **Spring Boot 3.4.1** (Web, Data JPA, Validation, Security)
- **PostgreSQL 16**
- **Flyway** (migrations versionadas)
- **Firebase Admin SDK** (verificação de login)
- **Lombok**
- **Maven**
- **Docker** (Dockerfile multi-stage + docker-compose)
- JUnit 5 + AssertJ + Mockito

**Front-end**
- **React 19** + **TypeScript**
- **Vite**
- **React Router** (SPA, rotas protegidas)
- **Firebase Authentication** (login com Google)

## Domínio

- **User** — usuário do sistema (autenticado via Google), pode ser responsável (`assignee`) por tarefas ou autor de comentários.
- **Project** — projeto de equipe, tem um `key` curto (ex.: `WEB`) e agrupa tarefas (`1:N` com `Task`).
- **Task** — tarefa associada a um `Project` e a um `User` responsável. Tem `key` legível gerado automaticamente (`WEB-1`, `WEB-2`...), `status` (`PENDING`, `IN_PROGRESS`, `DONE`), `priority` (`LOW`, `MEDIUM`, `HIGH`, `URGENT`) e `labels`.
- **Comment** — comentário de um usuário numa task (relação unidirecional, não precisa navegar de `Task` pra `Comment`).

## Estrutura do projeto

```
TaskFlow/  (back-end)
com.webapps.taskflow
├── entity/      → User, Project, Task, Comment, Status, Priority (enums) — mapeamento JPA + Lombok
├── repository/  → Spring Data JPA repositories
├── dtos/        → CreateRequest/UpdateRequest/Response records por entidade, com Bean Validation
├── mapper/      → conversão entidade ↔ DTO
├── service/     → regras de negócio, transações (@Transactional)
├── controller/  → REST controllers (finos, delegam pro service)
├── security/    → verificação do token do Firebase
└── config/      → SecurityConfig, FirebaseConfig

frontend/  (front-end)
src/
├── components/  → peças de UI reutilizáveis (ProjectCard, ProtectedRoute)
├── pages/       → telas ligadas a rotas
├── hooks/       → lógica de estado + efeitos reutilizável (useProjects, useProject)
├── services/    → chamadas à API e autenticação (Firebase)
└── types/       → interfaces TypeScript do domínio
```

A API não expõe as entidades JPA diretamente — todo `GET`/`POST`/`PUT`/`DELETE` passa por DTOs (`record` do Java), desacoplando o contrato da API do modelo de persistência.

## Autenticação

Login via **Google, através do Firebase Authentication**. O front-end autentica no navegador com o SDK do Firebase e envia o ID token em todo request (`Authorization: Bearer <token>`). O back-end verifica esse token com o **Firebase Admin SDK** e associa a um `User` local (criado automaticamente no primeiro login, por e-mail).

**Toda rota da API exige autenticação** — não há acesso anônimo.

## Endpoints atuais

Nos 3 recursos principais (`/users`, `/projects`, `/tasks`):

| Método | Rota              | Descrição                                                |
|--------|-------------------|-----------------------------------------------------------|
| GET    | `/{recurso}`      | Lista paginada (`?page=&size=&sort=`)                     |
| POST   | `/{recurso}`      | Cria (Bean Validation, `404` se referência não existir)   |
| PUT    | `/{recurso}/{id}` | Atualiza (Bean Validation, `404` se não existir)          |
| DELETE | `/{recurso}/{id}` | Soft delete (`404` se já excluído/inexistente)            |

`DELETE /projects/{id}` cascateia soft delete pras tasks do projeto. Toda `Task` criada recebe automaticamente um `key` sequencial dentro do `Project` (ex.: `WEB-1`, `WEB-2`).

Comentários (aninhados em `Task`):

| Método | Rota                       | Descrição                        |
|--------|----------------------------|-----------------------------------|
| GET    | `/tasks/{taskId}/comments` | Lista comentários da task         |
| POST   | `/tasks/{taskId}/comments` | Cria comentário (`404` se task/autor não existir) |

## Rodando localmente

### Configurar o Firebase (uma vez só)

1. Cria um projeto no [Firebase Console](https://console.firebase.google.com), habilita **Authentication → Sign-in method → Google**.
2. Gera uma chave de service account (**Project settings → Service accounts → Generate new private key**) e guarda o `.json` **fora do repositório**.
3. Aponta `firebase.service-account-path` (em `application.properties`, ou via variável de ambiente `FIREBASE_SERVICE_ACCOUNT_PATH`) pro caminho desse arquivo.
4. Substitui o config object em `frontend/src/services/firebase.ts` pelo do seu próprio projeto Firebase (não é segredo, mas é específico de cada projeto Firebase).

### Via Docker (back-end + banco)

```
docker compose up --build
```

Sobe a API (`localhost:8080`) e o Postgres juntos; o Flyway aplica as migrations automaticamente.

### Direto na IDE (back-end)

1. PostgreSQL rodando localmente, banco `taskflow` criado.
2. Ajustar `TaskFlow/src/main/resources/application.properties` (banco + caminho do service account do Firebase).
3. Rodar `TaskFlowApplication` (Java 21, precisa do plugin Lombok + annotation processing habilitados na IDE) — sobe em `http://localhost:8080`.

### Front-end

```
cd frontend
npm install
npm run dev
```

Sobe em `http://localhost:5173`.

## Status / próximos passos

- [x] Modelagem do domínio + persistência via JPA/Hibernate
- [x] API REST (`GET`/`POST`/`PUT`/`DELETE`) com DTOs, mappers e service layer
- [x] Bean Validation
- [x] Paginação nas listagens
- [x] Soft delete (com cascata Project → Task)
- [x] Migrations versionadas com Flyway
- [x] Testes unitários (entidades, mappers, services)
- [x] Dockerfile + docker-compose (app + banco)
- [x] Front-end React + TypeScript consumindo a API, com rotas (React Router)
- [x] Domínio expandido: `key` sequencial, prioridade, labels, comentários
- [x] Autenticação real (Google via Firebase), toda rota protegida
- [ ] Board Kanban no front-end
- [ ] Deploy público (back-end + front-end)
