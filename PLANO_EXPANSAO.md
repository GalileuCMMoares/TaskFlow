# Plano de Expansão — TaskFlow "Jira-like"

Continuação do projeto original ([`PLANO_DE_PROJETO.md`](./PLANO_DE_PROJETO.md), já concluído) — objetivo agora é evoluir o TaskFlow de um CRUD de aprendizado pra uma ferramenta de gestão de tarefas no estilo Jira, publicada online como projeto de portfólio.

## Escopo

**Nível de "pronto pra produção":** projeto pessoal publicado publicamente — single-tenant (um workspace só, sem isolamento multi-cliente/billing). O objetivo é ter algo real rodando na internet, não construir um produto SaaS multi-usuário completo.

## Novo domínio ✅ concluído

Mudanças nas entidades existentes + uma nova:

- **`Task`** ganhou:
  - `key` (`String`, ex.: `WEB-1`) — identificador legível, gerado sequencialmente por projeto.
  - `priority` (enum: `LOW`, `MEDIUM`, `HIGH`, `URGENT`).
  - `labels` (coleção de strings, `@ElementCollection`).
- **`Project`** ganhou `key` (definido na criação, imutável) e um contador interno (`nextTaskNumber`) pra gerar o `key` das tasks de forma sequencial — encapsulado no método de domínio `nextTaskKey()`.
- **`Comment`** (entidade nova): `id`, `content`, `author` (`User`), `task` (`Task`), `createdAt`. Relação **unidirecional** com `Task`/`User` (não precisa navegar de volta). Endpoints em `/tasks/{taskId}/comments`.

Migration feita sem quebrar dado existente (técnica expand → backfill → contract).

## Autenticação — login com Google via Firebase ✅ concluído

Mudança em relação ao desenho original deste plano: em vez de configurar OAuth2 "cru" no Google Cloud Console (`spring-boot-starter-oauth2-client` + JWT próprio), fomos de **Firebase Authentication** — o Google Cloud Console pediu configuração de faturamento que o Firebase (plano gratuito Spark) não exige pra esse uso.

Como ficou:
1. Front-end usa o SDK do Firebase (`signInWithPopup` + `GoogleAuthProvider`) — todo o fluxo OAuth2 com o Google é resolvido no navegador, sem o back-end precisar lidar com redirect URIs.
2. Front-end manda o **ID token do Firebase** em toda chamada (`Authorization: Bearer <token>`).
3. Back-end verifica esse token com o **Firebase Admin SDK** (`FirebaseAuth.verifyIdToken`) — sem emitir JWT próprio, o token do Firebase já cobre esse papel.
4. `User` local é criado automaticamente no primeiro login, por e-mail (com `UNIQUE` constraint garantindo consistência).

`SecurityConfig` exige autenticação em toda rota (`anyRequest().authenticated()`), com CORS configurado direto na cadeia de segurança (pegadinha real: o preflight `OPTIONS` precisa ficar liberado sem autenticação, senão o navegador bloqueia toda chamada com header customizado).

Credenciais (arquivo `.json` da service account do Firebase) guardadas fora do repositório, nunca commitadas.

## Board Kanban

Tela nova no front: tasks agrupadas em colunas por `status` (`PENDING` / `IN_PROGRESS` / `DONE`), reaproveitando o `GET /tasks` que já existe — é reorganização de dado que já vem da API, não precisa de endpoint novo. Drag-and-drop entre colunas (mudar o status arrastando o card) fica como **etapa avançada opcional**, depois do board estático funcionar — é uma biblioteca nova (`@dnd-kit` ou similar) e vale aprender isolado, sem misturar com a primeira versão do board.

## Deploy — colocando online

Recomendação: **Railway** ou **Fly.io** pro backend + Postgres gerenciado (ambos têm free tier, suportam deploy direto do `Dockerfile` que já existe, e Postgres gerenciado sem precisar administrar o próprio banco). Frontend: **Vercel** ou **Netlify** (deploy de build estático do Vite, free tier, HTTPS automático) — mais simples do que servir o front pelo próprio Spring Boot.

Trabalho de infra necessário:
- `application-prod.properties` (ou variáveis de ambiente) separando config de produção da de dev local.
- CORS do backend liberando o domínio real do frontend publicado (não mais só `localhost:5173`).
- Segredos (URL do banco, client secret do Google, chave de assinatura do JWT) só como variável de ambiente na plataforma de deploy — nunca no `application.properties` commitado.

## Ordem sugerida de implementação

1. ~~**Expandir o domínio**: `Comment` (entidade + DTO + mapper + service + controller + testes), `Task.priority`/`labels`, `key` sequencial por projeto.~~ ✅
2. ~~**Autenticação real**: login com Google via Firebase, substituindo o mock (`services/auth.ts` do front, `ProtectedRoute`).~~ ✅
3. **Board Kanban** no front (sem drag-and-drop ainda).
4. **UI de comentários** na tela de detalhe da task, exibição do `key` (`WEB-1`) em vez do `id` cru.
5. **Deploy**: escolher a plataforma, configurar env vars/segredos, publicar back e front, testar de ponta a ponta em produção.
6. *(Opcional, depois de tudo funcionando)* drag-and-drop no board.

Cada item continua seguindo o mesmo ritmo do projeto até aqui: um passo por vez, testado antes de seguir pro próximo.
