# Jura

Jura e um sistema de gerenciamento de usuarios, times, projetos, tarefas e relatorios desenvolvido em Java. O projeto usa MySQL para persistencia dos dados e JDBC para comunicacao com o banco.

O sistema foi desenvolvido como projeto academico da disciplina de Programacao de Solucoes Computacionais, no curso de Big Data e Inteligencia Analitica da Universidade Anhembi Morumbi.

## Funcionalidades

- Login de usuarios
- Recuperacao de senha por email e CPF
- Cadastro e edicao de usuarios
- Cadastro e edicao de times
- Gerenciamento de membros por time
- Cadastro e edicao de projetos
- Cadastro e edicao de tarefas
- Historico de alteracoes em entidades principais
- Relatorio de tarefas por status e por projeto

## Tecnologias

- Java
- MySQL
- JDBC

## Estrutura do projeto

```text
src/
  controller/   Menus e interacao com o usuario via console
  service/      Regras de negocio
  repository/   Acesso ao banco de dados
  model/        Entidades e modelos de relatorio
  enums/        Tipos fixos usados pelo sistema
```

## Como executar sem IDE

1. Instale o JDK.
2. Instale e inicie o MySQL.
3. Execute o arquivo `database.sql` no MySQL para criar o banco `jura` e as tabelas.
4. No Windows, rode `run.bat` na raiz do projeto.


O script pergunta a senha do usuario `root` do MySQL caso a variavel `DB_PASSWORD` nao esteja configurada.

Ao abrir o sistema pela primeira vez, use o botao **Criar usuario** na tela de login para cadastrar o primeiro usuario.

Para compilar sem executar:

```powershell
.\build.ps1
```

Para executar pelo PowerShell:

```powershell
.\run.ps1
```

## Banco de dados

O banco padrao se chama `jura`. A conexao esta configurada em:

```text
src/repository/DatabaseConnection.java
```

Por padrao, a aplicacao usa:

```text
jdbc:mysql://localhost:3306/jura
usuario: root
senha: variavel de ambiente DB_PASSWORD
```

## Documentacao complementar

- [Arquitetura](docs/architecture.md)
- [Banco de dados](docs/database.md)
- [Funcionalidades](docs/features.md)
- [Regras de negocio](docs/business-rules.md)
