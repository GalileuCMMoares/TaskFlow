# Plano de Projeto — Full-Stack Java

---

## Projeto — "TaskFlow" (Gerenciador de Tarefas de Equipe)

Aplicação web full-stack de gestão de tarefas e projetos de equipe. O domínio é simples de
entender, mas cobre **todos** os conceitos das trilhas do kickoff.

**Entidades:** `Usuário`, `Projeto`, `Tarefa` (com relacionamentos entre elas).

**Funcionalidades:**
- CRUD de projetos e tarefas.
- Tarefas associadas a projetos e a responsáveis (usuários).
- Listagem com paginação e filtros (status, responsável).
- Validação de dados de entrada.
- Soft delete (nada é apagado fisicamente).
- Autenticação com rotas protegidas no front-end.
- Front-end React consumindo a API Java.
- Ambiente todo dockerizado (app + banco).

---

## Onde cada conceito é abordado

### Java — a linguagem
Modelagem das classes de domínio (`Usuario`, `Projeto`, `Tarefa`):
- **Classes e objetos** → entidades do domínio.
- **Encapsulamento** → atributos privados com acesso controlado.
- **Herança / polimorfismo** → hierarquia de tipos de tarefa e comportamentos variáveis.
- **Interfaces** → contratos para serviços e repositórios.

### Banco — SQL & JPA
Persistência do domínio:
- **Modelagem relacional, normalização e chaves** → schema das tabelas.
- **SELECT / WHERE / JOIN / INSERT / UPDATE / DELETE** → queries de tarefas e projetos.
- **ORM com JPA/Hibernate** → `@Entity`, `@Id`, `@GeneratedValue`.
- **Relacionamentos** → `@OneToMany` / `@ManyToOne` (Projeto ↔ Tarefas, Usuário ↔ Tarefas).
- **JPQL e ciclo de vida das entidades**.

### Spring Boot — o framework
API REST sobre o domínio:
- **Setup** → Spring Initializr, Maven, Spring Web.
- **REST controllers (GET)** → listagem de projetos e tarefas.
- **POST** → `@PostMapping`, `@RequestBody` para criação.
- **DTOs e Java Records** → contratos de entrada/saída.
- **Spring Data JPA + migrations** → persistência e versionamento do schema.
- **Paginação** → `Pageable` na listagem.
- **Update e Soft Delete** → `@Transactional`.
- **Bean Validation** → `@NotBlank`, `@Valid` nos DTOs.

### Docker — ambientes & containers
Ambiente de desenvolvimento:
- **Dockerfile** → empacotamento da aplicação Spring Boot.
- **docker-compose** → sobe app + banco (PostgreSQL/MySQL) juntos.
- **Integração de banco via Docker** → banco containerizado no fluxo de dev.

### React — front-end
SPA em React + TypeScript:
- **Componentes e JSX/TSX** → telas de projetos e tarefas.
- **Props e composição** → reuso de componentes (cards, listas, formulários).
- **Estado e hooks** → `useState`, `useEffect`.
- **Eventos e renderização condicional** → interações e estados de UI.
- **Consumo de API** → integração com o back-end Java.
- **React Router** → rotas, parâmetros, rotas aninhadas e **rotas protegidas** (autenticação).

---

## Entregáveis
- Repositório Git com histórico organizado (branch/PR por etapa) e code review dos mentores.
- API REST documentada + front-end integrado (fluxo end-to-end).
- Ambiente dockerizado: `docker-compose up` sobe app + banco.
- README com arquitetura e instruções de execução.

---

## Pontos para validação
1. O domínio (TaskFlow) está adequado ou preferem algo alinhado a um produto real do time?
2. Preferência de banco: PostgreSQL ou MySQL?
3. Convenções e repositório do time a seguir.
