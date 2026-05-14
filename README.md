# Trabalho 01 - Desenvolvimento Web

Universidade Estadual de Ponta Grossa  
Curso de Engenharia de Computação  
Disciplina: Desenvolvimento Web

## Integrantes

Pedro Buture de Oliveira &&
Arthur Gregorio

## Descrição

API REST em Java com Spring Boot para gerenciamento de jogadores mensalistas e pagamentos.
# API de Mensalistas

Sistema web simplificado em Java com Spring Boot, Spring MVC e Spring Data JPA para gerenciar jogadores mensalistas e seus pagamentos.

## Como executar

Requisitos:

- JDK 8 ou superior
- Maven
Sem Maven instalado:

```bash
.\mvnw.cmd spring-boot:run
```
Com Maven instalado:

```bash
mvn spring-boot:run
```

A API ficara disponivel em:

```text
http://localhost:8080
```

O banco usado por padrao e H2 em memoria. O console fica em:

```text
http://localhost:8080/h2-console
```

Dados de conexao:

- JDBC URL: `jdbc:h2:mem:mensalistas`
- User: `sa`
- Password: vazio

## Modelo implementado

- `Jogador`
  - `cod_jogador` chave primaria
  - `nome`
  - `email`
  - `datanasc`
  - relacionamento `@OneToMany` com pagamentos

- `Pagamento`
  - `cod_pagamento` chave primaria
  - `ano`
  - `mes`
  - `valor`
  - `cod_jogador` chave estrangeira
  - relacionamento `@ManyToOne` com jogador

## Endpoints principais

### Jogadores

```http
GET /api/jogadores
GET /api/jogadores/{codJogador}
POST /api/jogadores
PUT /api/jogadores/{codJogador}
DELETE /api/jogadores/{codJogador}
GET /api/jogadores/{codJogador}/pagamentos
POST /api/jogadores/{codJogador}/pagamentos
```

Exemplo de criacao:

```json
{
  "nome": "Joao Silva",
  "email": "joao@email.com",
  "dataNasc": "1998-04-15"
}
```

### Pagamentos

```http
GET /api/pagamentos
GET /api/pagamentos?codJogador=1&ano=2026&mes=5
GET /api/pagamentos/{codPagamento}
POST /api/pagamentos
PUT /api/pagamentos/{codPagamento}
DELETE /api/pagamentos/{codPagamento}
```

Exemplo de criacao:

```json
{
  "ano": 2026,
  "mes": 5,
  "valor": 120.00,
  "codJogador": 1
}
```

Exemplo usando a rota do jogador:

```http
POST /api/jogadores/1/pagamentos
```

```json
{
  "ano": 2026,
  "mes": 5,
  "valor": 120.00
}
```

## Exemplos com PowerShell

Com a aplicacao rodando em `http://localhost:8080`, use os comandos abaixo no PowerShell.

### Jogadores

Listar jogadores:

```powershell
Invoke-RestMethod http://localhost:8080/api/jogadores
```

Buscar jogador por codigo:

```powershell
Invoke-RestMethod http://localhost:8080/api/jogadores/1
```

Criar jogador:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/jogadores -ContentType "application/json" -Body '{"nome":"Joao Silva","email":"joao@email.com","dataNasc":"1998-04-15"}'
```

Atualizar jogador:

```powershell
Invoke-RestMethod -Method Put -Uri http://localhost:8080/api/jogadores/1 -ContentType "application/json" -Body '{"nome":"Joao Silva Atualizado","email":"joao.atualizado@email.com","dataNasc":"1998-04-15"}'
```

Remover jogador:

```powershell
Invoke-RestMethod -Method Delete -Uri http://localhost:8080/api/jogadores/1
```

Listar pagamentos de um jogador:

```powershell
Invoke-RestMethod http://localhost:8080/api/jogadores/1/pagamentos
```

Criar pagamento pela rota do jogador:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/jogadores/1/pagamentos -ContentType "application/json" -Body '{"ano":2026,"mes":5,"valor":120.00}'
```

### Pagamentos

Listar pagamentos:

```powershell
Invoke-RestMethod http://localhost:8080/api/pagamentos
```

Filtrar pagamentos:

```powershell
Invoke-RestMethod "http://localhost:8080/api/pagamentos?codJogador=1&ano=2026&mes=5"
```

Buscar pagamento por codigo:

```powershell
Invoke-RestMethod http://localhost:8080/api/pagamentos/1
```

Criar pagamento:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/pagamentos -ContentType "application/json" -Body '{"ano":2026,"mes":5,"valor":120.00,"codJogador":1}'
```

Atualizar pagamento:

```powershell
Invoke-RestMethod -Method Put -Uri http://localhost:8080/api/pagamentos/1 -ContentType "application/json" -Body '{"ano":2026,"mes":6,"valor":150.00,"codJogador":1}'
```

Remover pagamento:

```powershell
Invoke-RestMethod -Method Delete -Uri http://localhost:8080/api/pagamentos/1
```

## Observacoes

- Nao ha interface grafica, apenas API REST.
- O relacionamento entre entidades foi mapeado com `@OneToMany` e `@ManyToOne`.
- Existe validacao dos campos obrigatorios, tamanho de texto, email, ano, mes e valor.
- Um mesmo jogador nao pode ter dois pagamentos para o mesmo ano e mes.
