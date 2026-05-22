# Arquitetura

O Jura segue uma organizacao em camadas simples. Essa separacao ajuda a manter a interacao com o usuario, as regras de negocio e o acesso ao banco em responsabilidades diferentes.

## Fluxo principal

```text
Controller -> Service -> Repository -> MySQL
```

## Camadas

### Controller

Os controllers ficam em `src/controller`.

Responsabilidades:

- Exibir menus no console
- Ler dados digitados pelo usuario
- Chamar os services adequados
- Mostrar mensagens de sucesso, erro ou relatorio

Exemplos:

- `Controller`
- `UserController`
- `TeamController`
- `TeamMemberController`
- `ProjectController`
- `TaskController`
- `ReportController`

### Service

Os services ficam em `src/service`.

Responsabilidades:

- Aplicar regras de negocio
- Validar permissoes
- Validar existencia de usuarios, times e projetos
- Coordenar chamadas aos repositories

Exemplos:

- `UserService`
- `TeamService`
- `TeamMemberService`
- `ProjectService`
- `TaskService`
- `ReportService`

### Repository

Os repositories ficam em `src/repository`.

Responsabilidades:

- Abrir conexao com o banco
- Executar comandos SQL
- Salvar dados nas tabelas principais
- Salvar historico nas tabelas de historico
- Carregar dados persistidos

Exemplos:

- `UserRepository`
- `TeamRepository`
- `TeamMemberRepository`
- `ProjectRepository`
- `TaskRepository`
- `ReportRepository`

### Model

Os models ficam em `src/model`.

Responsabilidades:

- Representar entidades do dominio
- Guardar dados em memoria durante a execucao
- Expor getters e setters usados pelas outras camadas

Exemplos:

- `User`
- `Team`
- `TeamMember`
- `Project`
- `Task`
- `TaskStatusReport`

### Enums

Os enums ficam em `src/enums`.

Responsabilidades:

- Padronizar valores fixos
- Evitar status e perfis escritos de formas diferentes

Exemplos:

- `ProfileType`
- `StatusProjects`
- `StatusTasks`

## Relatorios

Os relatorios seguem o mesmo fluxo em camadas:

```text
ReportController -> ReportService -> ReportRepository -> MySQL
```

O `ReportRepository` usa SQL com `GROUP BY` para contar tarefas por projeto e por status. O resultado e organizado em `TaskStatusReport`.
