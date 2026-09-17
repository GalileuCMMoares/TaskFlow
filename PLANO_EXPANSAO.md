# Plano de Expansão — TaskFlow "Jira-like"

Continuação do projeto original ([`PLANO_DE_PROJETO.md`](./PLANO_DE_PROJETO.md), já concluído) — objetivo agora é evoluir o TaskFlow de um CRUD de aprendizado pra uma ferramenta de gestão de tarefas no estilo Jira, publicada online como projeto de portfólio.

## Escopo

**Nível de "pronto pra produção":** projeto pessoal publicado publicamente — single-tenant (um workspace só, sem isolamento multi-cliente/billing). O objetivo é ter algo real rodando na internet, não construir um produto SaaS multi-usuário completo.

## Novo domínio

Mudanças nas entidades existentes + uma nova:

- **`Task`** ganha:
  - `key` (`String`, ex.: `TASKFLOW-1`) — identificador legível, gerado sequencialmente por projeto.
  - `priority` (enum: `LOW`, `MEDIUM`, `HIGH`, `URGENT`).
  - `labels` (coleção de strings, `@ElementCollection` — mais simples que uma entidade `Label` separada pra esse escopo).
- **`Project`** ganha um contador interno (`nextTaskNumber` ou equivalente) pra gerar o `key` das tasks de forma sequencial e seguro contra concorrência (não pode ser só `tasks.size() + 1` — colide se duas tasks forem criadas ao mesmo tempo).
- **`User`** ganha campos pra login via Google (`googleId` ou `email` como identificador de login) — sem campo de senha, ver seção de autenticação abaixo.
- **`Comment`** (nova entidade): `id`, `content`, `author` (`User`, `@ManyToOne`), `task` (`Task`, `@ManyToOne`), `createdAt`. Segue o mesmo padrão já estabelecido (entidade → DTO → mapper → service → controller).

## Autenticação — login com Google, sessão com JWT próprio

Simplificação importante: em vez de dois sistemas de auth em paralelo (senha própria + Google), a ideia é **um fluxo só**:

1. Front-end usa "Sign in with Google" (OAuth2) pra provar a identidade do usuário.
2. Backend recebe esse identity token, valida com o Google, encontra ou cria o `User` correspondente (por e-mail).
3. Backend emite **seu próprio JWT**, que o front usa nas chamadas subsequentes à API (substituindo o mock de `localStorage` atual).

Isso evita ter que implementar cadastro de senha, hash, fluxo de "esqueci minha senha", etc. — menos código, mais seguro, e ainda cobre os dois itens do plano (`Spring Security + JWT` e `login Google`) porque são a mesma peça, não duas.

No backend: `spring-boot-starter-oauth2-client` + `spring-boot-starter-security`, endpoint de callback do Google, filtro JWT pras rotas protegidas (substituindo o `ProtectedRoute` mockado do front, que vira real).

**Credenciais do Google OAuth (client id/secret) nunca vão pro repositório** — variáveis de ambiente, consistente com a regra já estabelecida de repo público sem segredo nenhum versionado.

## Board Kanban

Tela nova no front: tasks agrupadas em colunas por `status` (`PENDING` / `IN_PROGRESS` / `DONE`), reaproveitando o `GET /tasks` que já existe — é reorganização de dado que já vem da API, não precisa de endpoint novo. Drag-and-drop entre colunas (mudar o status arrastando o card) fica como **etapa avançada opcional**, depois do board estático funcionar — é uma biblioteca nova (`@dnd-kit` ou similar) e vale aprender isolado, sem misturar com a primeira versão do board.

## Deploy — colocando online

Recomendação: **Railway** ou **Fly.io** pro backend + Postgres gerenciado (ambos têm free tier, suportam deploy direto do `Dockerfile` que já existe, e Postgres gerenciado sem precisar administrar o próprio banco). Frontend: **Vercel** ou **Netlify** (deploy de build estático do Vite, free tier, HTTPS automático) — mais simples do que servir o front pelo próprio Spring Boot.

Trabalho de infra necessário:
- `application-prod.properties` (ou variáveis de ambiente) separando config de produção da de dev local.
- CORS do backend liberando o domínio real do frontend publicado (não mais só `localhost:5173`).
- Segredos (URL do banco, client secret do Google, chave de assinatura do JWT) só como variável de ambiente na plataforma de deploy — nunca no `application.properties` commitado.

## Ordem sugerida de implementação

1. **Expandir o domínio**: `Comment` (entidade + DTO + mapper + service + controller + testes), `Task.priority`/`labels`, `key` sequencial por projeto.
2. **Autenticação real**: Google OAuth2 + JWT próprio, substituindo o mock (`services/auth.ts` do front, `ProtectedRoute`).
3. **Board Kanban** no front (sem drag-and-drop ainda).
4. **UI de comentários** na tela de detalhe da task, exibição do `key` (`TASKFLOW-1`) em vez do `id` cru.
5. **Deploy**: escolher a plataforma, configurar env vars/segredos, publicar back e front, testar de ponta a ponta em produção.
6. *(Opcional, depois de tudo funcionando)* drag-and-drop no board.

Cada item continua seguindo o mesmo ritmo do projeto até aqui: um passo por vez, testado antes de seguir pro próximo.
