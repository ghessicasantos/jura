# Banco de Dados

O sistema usa MySQL. O script de criacao esta no arquivo `database.sql` na raiz do projeto.

## Configuracao

Banco padrao:

```sql
CREATE DATABASE IF NOT EXISTS jura;
USE jura;
```

A conexao e configurada em `src/repository/DatabaseConnection.java`.

```text
URL: jdbc:mysql://localhost:3306/jura
Usuario: root
Senha: variavel de ambiente DB_PASSWORD
```

## Tabelas principais

### users

Armazena os usuarios do sistema.

Campos principais:

- `full_name`
- `cpf`
- `email`
- `cargo`
- `login`
- `password`
- `profile_name`
- `profile_type`

### teams

Armazena os times cadastrados.

Campos principais:

- `team_name`
- `description`
- `team_owner_email`
- `created_at`

### team_members

Armazena o vinculo entre usuarios e times.

Campos principais:

- `team_member_email`
- `team_name`
- `member_status`

O campo `member_status` indica se o usuario esta ativo no time. Quando um membro e removido do time, o vinculo nao deve ser apagado; o status deve ser atualizado para inativo.

### projects

Armazena os projetos.

Campos principais:

- `project_name`
- `description`
- `start_date`
- `finish_date`
- `status`
- `project_manager_email`
- `team_owner_name`

### tasks

Armazena as tarefas dos projetos.

Campos principais:

- `task_title`
- `description`
- `start_date`
- `finish_date`
- `status`
- `assigned_user_email`
- `project_name`

## Tabelas de historico

O sistema usa tabelas de historico para preservar estados anteriores antes de alteracoes relevantes.

Tabelas de historico:

- `users_history`
- `teams_history`
- `team_members_history`
- `project_history`
- `tasks_history`

## Relatorios

O relatorio de tarefas por status e projeto e calculado a partir da tabela `tasks`.

Consulta base:

```sql
SELECT project_name, status, COUNT(*) AS total
FROM tasks
GROUP BY project_name, status
ORDER BY project_name, status;
```

Para um projeto especifico:

```sql
SELECT status, COUNT(*) AS total
FROM tasks
WHERE project_name = ?
GROUP BY status
ORDER BY status;
```

Nao existe tabela separada para relatorio, porque os dados sao derivados das tarefas cadastradas.
