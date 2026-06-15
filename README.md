# Feira / TipoFeira — Migração Console → Web

CRUD de **Feira** e **TipoFeira** migrado de uma aplicação Java de console para a web:
**backend Spring Boot 4 (REST + JPA/H2)** e **frontend Angular 22**.

Disciplina: Projeto e Arquitetura de Sistemas (UNIFOR). Domínio do grupo: **Feira / TipoFeira**
(portado do console [`crud-grasp-feira-tipo`](https://github.com/brunocaminha/crud-grasp-feira-tipo)).

## Visão geral

- **Feira**: nome, logradouro, bairro, flag *ativa* e um **TipoFeira** (obrigatório).
- **TipoFeira**: nome — classificador da feira (ex.: "Orgânica", "Noturna").
- **Regras de negócio** (ficam só no service): nome de tipo é único; tipo em uso não pode ser
  removido; o tipo informado deve existir; duas feiras **ativas** não podem dividir o mesmo
  endereço (logradouro + bairro, *case-insensitive*).

## Requisitos

- **Java 17+** e **Maven 3.9+** (backend)
- **Node 20.19+** (testado com Node 24) e **Angular CLI 22** (frontend)

## Como rodar

### Backend (porta 8080)

```bash
cd backend/feira-tipo-api
mvn spring-boot:run
```

- API: <http://localhost:8080/api>
- Swagger UI: <http://localhost:8080/swagger-ui.html>
- Console H2: <http://localhost:8080/h2-console> — JDBC `jdbc:h2:mem:feiratipodb`, usuário `sa`, sem senha.

### Frontend (porta 4200)

```bash
cd frontend/feira-tipo-web
npm install
ng serve
```

- App: <http://localhost:4200>
- O CORS já está liberado no backend para `http://localhost:4200`.

> Suba o **backend antes** do frontend para as listas carregarem.

## Endpoints

| Método | Rota | Descrição | Status |
|--------|------|-----------|--------|
| GET | `/api/feiras` | Lista feiras | 200 |
| POST | `/api/feiras` | Cadastra feira | 201 / 400 / 422 |
| DELETE | `/api/feiras/{id}` | Remove feira | 204 |
| GET | `/api/tipos-feira` | Lista tipos | 200 |
| POST | `/api/tipos-feira` | Cadastra tipo | 201 / 400 / 422 |
| DELETE | `/api/tipos-feira/{id}` | Remove tipo | 204 / 422 (em uso) |

- **400** — erro de validação de campo (Bean Validation); o corpo traz a lista de campos.
- **422** — violação de regra de negócio (`RegraNegocioException`).

## Estrutura

```
backend/feira-tipo-api/   Spring Boot 4 — domain · repository · service · controller · dto · exception · config
frontend/feira-tipo-web/  Angular 22  — services + components (lista-feiras · form-feira · lista-tipos)
```

## Tecnologias

Spring Boot 4 · Spring Data JPA · H2 · Bean Validation · springdoc/Swagger · Angular 22 · RxJS · Bootstrap 5.
