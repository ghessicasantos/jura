# Funcionalidades

Este documento resume as principais funcionalidades do sistema Jura.

## Login

O sistema inicia solicitando email e senha do usuario.

Se o login falhar, o usuario pode:

- tentar novamente;
- recuperar a senha.

## Recuperacao de senha

A recuperacao de senha solicita:

- email;
- CPF;
- nova senha.

Se o email existir e o CPF corresponder ao usuario, o sistema salva o estado anterior em `users_history` e atualiza a senha na tabela `users`.

## Usuarios

Funcionalidades disponiveis:

- criar usuario;
- editar nome completo;
- editar email;
- editar cargo;
- editar nome do perfil;
- editar tipo de perfil.

Os usuarios possuem um tipo de perfil definido pelo enum `ProfileType`.

## Times

Funcionalidades disponiveis:

- criar time;
- editar nome do time;
- editar descricao;
- alterar responsavel pelo time;
- acessar o menu de membros do time.

Cada time possui:

- nome;
- descricao;
- responsavel;
- data de criacao;
- lista de membros;
- lista de projetos.

## Membros do time

Funcionalidades disponiveis:

- listar membros ativos por time;
- adicionar usuario a um time;
- remover usuario de um time.

Antes de escolher o time, o sistema mostra os times cadastrados.

A remocao de membro nao apaga o vinculo do banco. O sistema salva o estado anterior em `team_members_history` e marca o membro como inativo em `team_members`.

## Projetos

Funcionalidades disponiveis:

- criar projeto;
- editar nome;
- editar descricao;
- editar data de inicio;
- editar data de termino;
- editar status;
- alterar gerente do projeto;
- alterar time do projeto.

Cada projeto deve estar vinculado a um time e a um gerente/responsavel.

## Tarefas

Funcionalidades disponiveis:

- criar tarefa;
- editar informacoes da tarefa;
- associar tarefa a um projeto;
- associar tarefa a um usuario responsavel;
- controlar status da tarefa.

Os status de tarefa sao definidos pelo enum `StatusTasks`:

- `PENDING`
- `IN_PROGRESS`
- `COMPLETED`
- `CANCELED`

## Relatorios

O menu de relatorios permite:

- visualizar quantidade de tarefas por status em todos os projetos;
- visualizar quantidade de tarefas por status em um projeto especifico.

O relatorio e calculado por SQL usando a tabela `tasks`.

Exemplo de saida:

```text
Projeto: Sistema Jura
PENDING: 3
IN_PROGRESS: 2
COMPLETED: 5
CANCELED: 1
Total: 11
```
