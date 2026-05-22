# Regras de Negocio

Este documento descreve as principais regras aplicadas pelo sistema.

## Usuarios

- O login e feito por email e senha.
- A recuperacao de senha valida email e CPF.
- A nova senha nao pode ser vazia.
- Antes de alterar a senha, o estado anterior do usuario e salvo em `users_history`.
- A alteracao do tipo de perfil depende da permissao definida no proprio usuario.

## Times

- Um time precisa ter um responsavel.
- Nao pode existir outro time com o mesmo nome.
- A alteracao do responsavel do time exige permissao de edicao de nivel 1.
- Alteracoes relevantes em time salvam o estado anterior em `teams_history`.

## Membros do time

- Um usuario so pode ser adicionado a um time existente.
- Um usuario so pode ser adicionado se existir no cadastro de usuarios.
- Nao e permitido adicionar novamente um membro que ja esta ativo no mesmo time.
- Se um usuario ja fez parte do time e esta inativo, ele pode ser reativado.
- Remover um membro nao apaga o historico.
- Ao remover um membro, o sistema salva o estado anterior em `team_members_history`.
- A remocao altera `member_status` para inativo na tabela `team_members`.
- A listagem de membros por time mostra apenas membros ativos.

## Projetos

- Um projeto precisa ter um gerente/responsavel.
- Um projeto precisa estar vinculado a um time.
- Nao pode existir outro projeto com o mesmo nome.
- Usuarios com perfil de colaborador nao podem ser responsaveis por projeto.
- Alteracoes relevantes em projeto salvam o estado anterior em `project_history`.
- Para editar informacoes do projeto, o usuario precisa ter permissao ou ser o gerente do projeto.

## Tarefas

- Uma tarefa precisa estar vinculada a um projeto.
- Uma tarefa precisa ter um usuario responsavel.
- O status da tarefa deve ser um valor valido do enum `StatusTasks`.
- Alteracoes relevantes em tarefa devem salvar o estado anterior em `tasks_history`.

## Relatorios

- Relatorios nao sao salvos em tabela propria.
- Relatorios sao calculados a partir dos dados atuais da tabela `tasks`.
- O relatorio geral mostra todos os projetos cadastrados.
- Projetos sem tarefas aparecem com contagem zero para todos os status.
- O relatorio por projeto so e exibido se o projeto existir.

## Historico

O sistema evita apagar informacoes importantes. Em vez disso, usa tabelas de historico para registrar o estado anterior antes de mudancas relevantes.

Esse comportamento e aplicado principalmente em:

- usuarios;
- times;
- membros de time;
- projetos;
- tarefas.
