# Biblioteca Digital - Sistema de Gestão

Sistema de gestão de biblioteca desenvolvido com Domain-Driven Design (DDD), SOLID e arquitetura preparada para evolução para microserviços.

## Pré-requisitos

### Software Necessário

- **Java Development Kit (JDK) 17 ou superior**
  - Verificar instalação: `java -version`
  - Download: <https://adoptium.net/> ou <https://www.oracle.com/java/technologies/downloads/>

- **Maven 3.6 ou superior**
  - Verificar instalação: `mvn -version`
  - Download: <https://maven.apache.org/download.cgi>

- **Git** (para clonar o repositório)
  - Verificar instalação: `git --version`
  - Download: <https://git-scm.com/downloads>

## Como Executar o Projeto

### 1. Clonar o Repositório

```bash
git clone <url-do-repositorio>
cd biblioteca
```

### 2. Compilar o Projeto

**Linux/Mac:**

```bash
./mvnw clean install
```

**Windows:**

```bash
mvnw.cmd clean install
```

### 3. Executar a Aplicação

**Linux/Mac:**

```bash
./mvnw spring-boot:run
```

**Windows:**

```bash
mvnw.cmd spring-boot:run
```

### 4. Verificar se está Funcionando

Abra o navegador e acesse:

- API Health Check: <http://localhost:8080/api/health>
- H2 Console: <http://localhost:8080/h2-console>

### 5. Configurar Acesso ao H2 Console

Na tela do H2 Console, configure:

```bash
JDBC URL: jdbc:h2:file:./data/biblioteca
User Name: sa
Password: (deixe em branco)
```

Clique em "Connect" para acessar o banco de dados.

## Executar Testes

```bash
./mvnw test
```

## Estrutura do Banco de Dados

O banco de dados H2 será criado automaticamente na pasta `./data/` na primeira execução.

O sistema já vem com dados de exemplo:

- 5 livros cadastrados
- 3 usuários (estudante, professor e comum)

## Logs

Os logs da aplicação são exibidos no console. Nível de log padrão: DEBUG para `com.example` e INFO para outros pacotes.

---

## Documentação da API

### Informações Gerais

**Base URL:** `http://localhost:8080/api`

**Formato de Dados:** JSON

**Autenticação:** Não implementada

**CORS:** Permitido para todas as origens

---

### Endpoints - Catálogo de Livros

#### 1. Listar Todos os Livros

```http
GET /catalog/books
```

**Query Parameters:**

| Parâmetro     | Tipo    | Obrigatório | Descrição                                                  |
|---------------|---------|-------------|------------------------------------------------------------|
| availableOnly | boolean | Não         | Se true, retorna apenas livros disponíveis (padrão: false) |

**Exemplo de Request:**

```bash
curl http://localhost:8080/api/catalog/books
curl http://localhost:8080/api/catalog/books?availableOnly=true
```

**Response (200 OK):**

```json
[
  {
    "id": "book-001",
    "isbn": "9780132350884",
    "title": "Clean Code",
    "authorName": "Robert C. Martin",
    "category": "Programming",
    "availableQuantity": 3,
    "totalQuantity": 5
  },
  {
    "id": "book-002",
    "isbn": "9780321125215",
    "title": "Domain-Driven Design",
    "authorName": "Eric Evans",
    "category": "Architecture",
    "availableQuantity": 2,
    "totalQuantity": 3
  }
]
```

---

#### 2. Buscar Livro por ID

```http
GET /catalog/books/{id}
```

**Path Parameters:**

| Parâmetro | Tipo   | Descrição   |
|-----------|--------|-------------|
| id        | string | ID do livro |

**Exemplo de Request:**

```bash
curl http://localhost:8080/api/catalog/books/book-001
```

**Response (200 OK):**

```json
{
  "id": "book-001",
  "isbn": "9780132350884",
  "title": "Clean Code",
  "authorName": "Robert C. Martin",
  "category": "Programming",
  "availableQuantity": 3,
  "totalQuantity": 5
}
```

**Response (404 Not Found):**

```json
{
  "message": "Livro não encontrado com ID: book-999"
}
```

---

#### 3. Buscar Livros por Título ou Autor

```http
GET /catalog/books/search
```

**Query Parameters:**

| Parâmetro | Tipo   | Obrigatório | Descrição                                   |
|-----------|--------|-------------|---------------------------------------------|
| q         | string | Sim         | Termo de busca (busca em título e autor)    |

**Exemplo de Request:**

```bash
curl "http://localhost:8080/api/catalog/books/search?q=clean"
```

**Response (200 OK):**

```json
[
  {
    "id": "book-001",
    "isbn": "9780132350884",
    "title": "Clean Code",
    "authorName": "Robert C. Martin",
    "category": "Programming",
    "availableQuantity": 3,
    "totalQuantity": 5
  }
]
```

---

#### 4. Cadastrar Novo Livro

```http
POST /catalog/books
```

**Request Body:**

```json
{
  "isbn": "9780134685991",
  "title": "Effective Java",
  "authorName": "Joshua Bloch",
  "category": "Programming",
  "totalQuantity": 3
}
```

**Campos:**

| Campo         | Tipo    | Obrigatório | Descrição                                          |
|---------------|---------|-------------|----------------------------------------------------|
| isbn          | string  | Sim         | ISBN do livro (10 ou 13 dígitos)                   |
| title         | string  | Sim         | Título do livro (3–255 caracteres)                 |
| authorName    | string  | Sim         | Nome do autor (3–255 caracteres)                   |
| category      | string  | Sim         | Categoria do livro (1–100 caracteres)              |
| totalQuantity | integer | Sim         | Quantidade total de exemplares (1–1000)            |

**Exemplo de Request:**

```bash
curl -X POST http://localhost:8080/api/catalog/books \
  -H "Content-Type: application/json" \
  -d '{
    "isbn": "9780134685991",
    "title": "Effective Java",
    "authorName": "Joshua Bloch",
    "category": "Programming",
    "totalQuantity": 3
  }'
```

**Response (201 Created):**

```json
{
  "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "isbn": "9780134685991",
  "title": "Effective Java",
  "authorName": "Joshua Bloch",
  "category": "Programming",
  "availableQuantity": 3,
  "totalQuantity": 3
}
```

**Response (400 Bad Request):**

```json
{
  "message": "Já existe um livro cadastrado com o ISBN: 9780134685991"
}
```

---

### Endpoints - Usuários

#### 1. Listar Todos os Usuários

```http
GET /users
```

**Exemplo de Request:**

```bash
curl http://localhost:8080/api/users
```

**Response (200 OK):**

```json
[
  {
    "id": "user-001",
    "name": "João Silva",
    "email": "joao.silva@email.com",
    "cpf": "123.456.789-01",
    "userType": "STUDENT",
    "maxBooks": 3,
    "loanDays": 14
  },
  {
    "id": "user-002",
    "name": "Maria Santos",
    "email": "maria.santos@email.com",
    "cpf": "987.654.321-09",
    "userType": "PROFESSOR",
    "maxBooks": 5,
    "loanDays": 30
  }
]
```

---

#### 2. Buscar Usuário por ID

```http
GET /users/{id}
```

**Path Parameters:**

| Parâmetro | Tipo   | Descrição     |
|-----------|--------|---------------|
| id        | string | ID do usuário |

**Exemplo de Request:**

```bash
curl http://localhost:8080/api/users/user-001
```

**Response (200 OK):**

```json
{
  "id": "user-001",
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "cpf": "123.456.789-01",
  "userType": "STUDENT",
  "maxBooks": 3,
  "loanDays": 14
}
```

**Response (404 Not Found):**

```json
{
  "message": "Usuário não encontrado com ID: user-999"
}
```

---

#### 3. Cadastrar Novo Usuário

```http
POST /users
```

**Request Body:**

```json
{
  "name": "Ana Costa",
  "email": "ana.costa@email.com",
  "cpf": "12345678901",
  "userType": "STUDENT"
}
```

**Campos:**

| Campo    | Tipo   | Obrigatório | Descrição                                              |
|----------|--------|-------------|--------------------------------------------------------|
| name     | string | Sim         | Nome completo (3–255 caracteres)                       |
| email    | string | Sim         | Email válido                                           |
| cpf      | string | Sim         | CPF (11 dígitos, com ou sem formatação)                |
| userType | string | Sim         | Tipo: `STUDENT`, `PROFESSOR` ou `COMMON`               |

**Tipos de Usuário:**

| Tipo       | Limite de Livros | Dias de Empréstimo |
|------------|------------------|--------------------|
| STUDENT    | 3                | 14                 |
| PROFESSOR  | 5                | 30                 |
| COMMON     | 2                | 7                  |

**Exemplo de Request:**

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Ana Costa",
    "email": "ana.costa@email.com",
    "cpf": "12345678901",
    "userType": "STUDENT"
  }'
```

**Response (201 Created):**

```json
{
  "id": "b2c3d4e5-f6g7-8901-bcde-f12345678901",
  "name": "Ana Costa",
  "email": "ana.costa@email.com",
  "cpf": "123.456.789-01",
  "userType": "STUDENT",
  "maxBooks": 3,
  "loanDays": 14
}
```

**Response (400 Bad Request):**

```json
{
  "message": "Já existe um usuário cadastrado com o CPF: 123.456.789-01"
}
```

---

### Endpoints - Empréstimos

#### 1. Emprestar Livro

```http
POST /lending/loans
```

**Request Body:**

```json
{
  "userId": "user-001",
  "bookId": "book-001"
}
```

**Campos:**

| Campo   | Tipo   | Obrigatório | Descrição     |
|---------|--------|-------------|---------------|
| userId  | string | Sim         | ID do usuário |
| bookId  | string | Sim         | ID do livro   |

**Exemplo de Request:**

```bash
curl -X POST http://localhost:8080/api/lending/loans \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "user-001",
    "bookId": "book-001"
  }'
```

**Response (201 Created):**

```json
{
  "id": "c3d4e5f6-g7h8-9012-cdef-123456789012",
  "userId": "user-001",
  "userName": "João Silva",
  "bookId": "book-001",
  "bookTitle": "Clean Code",
  "borrowedAt": "2026-01-27",
  "dueDate": "2026-02-10",
  "returnedAt": null,
  "status": "ACTIVE",
  "fineAmount": 0.0
}
```

**Response (400 Bad Request) - Livro Indisponível:**

```json
{
  "message": "Livro 'Clean Code' não está disponível para empréstimo"
}
```

**Response (400 Bad Request) - Limite Atingido:**

```json
{
  "message": "Usuário 'João Silva' atingiu o limite de 3 empréstimos simultâneos"
}
```

**Response (404 Not Found) - Usuário ou Livro Não Existe:**

```json
{
  "message": "Usuário não encontrado com ID: user-999"
}
```

---

#### 2. Devolver Livro

```http
PUT /lending/loans/{loanId}/return
```

**Path Parameters:**

| Parâmetro | Tipo   | Descrição        |
|-----------|--------|------------------|
| loanId    | string | ID do empréstimo |

**Exemplo de Request:**

```bash
curl -X PUT http://localhost:8080/api/lending/loans/c3d4e5f6-g7h8-9012-cdef-123456789012/return
```

**Response (200 OK) - Devolução no Prazo:**

```json
{
  "id": "c3d4e5f6-g7h8-9012-cdef-123456789012",
  "userId": "user-001",
  "userName": "João Silva",
  "bookId": "book-001",
  "bookTitle": "Clean Code",
  "borrowedAt": "2026-01-27",
  "dueDate": "2026-02-10",
  "returnedAt": "2026-02-05",
  "status": "RETURNED",
  "fineAmount": 0.0
}
```

**Response (200 OK) - Devolução com Atraso:**

```json
{
  "id": "c3d4e5f6-g7h8-9012-cdef-123456789012",
  "userId": "user-001",
  "userName": "João Silva",
  "bookId": "book-001",
  "bookTitle": "Clean Code",
  "borrowedAt": "2026-01-01",
  "dueDate": "2026-01-15",
  "returnedAt": "2026-01-27",
  "status": "RETURNED",
  "fineAmount": 24.0
}
```

**Cálculo de Multa:**

- Valor: R$ 2,00 por dia de atraso
- Exemplo: 12 dias de atraso = R$ 24,00

**Response (400 Bad Request) - Livro Já Devolvido:**

```json
{
  "message": "Livro já foi devolvido"
}
```

**Response (404 Not Found):**

```json
{
  "message": "Empréstimo não encontrado com ID: loan-999"
}
```

---

#### 3. Listar Empréstimos Ativos do Usuário

```http
GET /lending/loans/user/{userId}
```

**Path Parameters:**

| Parâmetro | Tipo   | Descrição     |
|-----------|--------|---------------|
| userId    | string | ID do usuário |

**Exemplo de Request:**

```bash
curl http://localhost:8080/api/lending/loans/user/user-001
```

**Response (200 OK):**

```json
[
  {
    "id": "c3d4e5f6-g7h8-9012-cdef-123456789012",
    "userId": "user-001",
    "userName": "João Silva",
    "bookId": "book-001",
    "bookTitle": "Clean Code",
    "borrowedAt": "2026-01-27",
    "dueDate": "2026-02-10",
    "returnedAt": null,
    "status": "ACTIVE",
    "fineAmount": 0.0
  },
  {
    "id": "d4e5f6g7-h8i9-0123-defg-234567890123",
    "userId": "user-001",
    "userName": "João Silva",
    "bookId": "book-002",
    "bookTitle": "Domain-Driven Design",
    "borrowedAt": "2026-01-20",
    "dueDate": "2026-02-03",
    "returnedAt": null,
    "status": "ACTIVE",
    "fineAmount": 0.0
  }
]
```

---

## Validações

### ISBN

- Aceita 10 ou 13 dígitos
- Pode incluir hífens (serão removidos automaticamente)
- Exemplos válidos: `9780132350884`, `978-0-13-235088-4`, `0132350882`

### CPF

- Aceita 11 dígitos
- Pode incluir pontos e hífen (serão removidos automaticamente)
- Valida dígitos verificadores
- Exemplos válidos: `12345678901`, `123.456.789-01`

### Email

- Formato padrão de email: `usuario@dominio.com`

---

## Dados de Exemplo (Pré-cadastrados)

### Livros

| ID        | Título                      | Autor              | ISBN           | Disponível |
|-----------|-----------------------------|--------------------|----------------|------------|
| book-001  | Clean Code                  | Robert C. Martin   | 9780132350884  | 3/5        |
| book-002  | Domain-Driven Design        | Eric Evans         | 9780321125215  | 2/3        |
| book-003  | Design Patterns             | Gang of Four       | 9780201633610  | 1/3        |
| book-004  | Effective Java              | Joshua Bloch       | 9780134685991  | 2/2        |
| book-005  | Head First Design Patterns  | Eric Freeman       | 9780596007126  | 4/4        |

### Usuários

| ID        | Nome            | Email                      | CPF               | Tipo       |
|-----------|-----------------|----------------------------|-------------------|------------|
| user-001  | João Silva      | <joao.silva@email.com>     | 123.456.789-01    | STUDENT    |
| user-002  | Maria Santos    | <maria.santos@email.com>   | 987.654.321-09    | PROFESSOR  |
| user-003  | Pedro Oliveira  | <pedro.oliveira@email.com> | 111.222.333-44    | COMMON     |

---

### Exemplos de Uso Completo

#### Fluxo: Emprestar e Devolver Livro

```bash
# 1. Listar livros disponíveis
curl http://localhost:8080/api/catalog/books?availableOnly=true

# 2. Verificar empréstimos ativos do usuário
curl http://localhost:8080/api/lending/loans/user/user-001

# 3. Emprestar livro
curl -X POST http://localhost:8080/api/lending/loans \
  -H "Content-Type: application/json" \
  -d '{"userId": "user-001", "bookId": "book-001"}'

# 4. Devolver livro (substitua LOAN_ID pelo ID retornado no passo 3)
curl -X PUT http://localhost:8080/api/lending/loans/LOAN_ID/return
```

#### Fluxo: Cadastrar Livro e Usuário

```bash
# 1. Cadastrar novo livro
curl -X POST http://localhost:8080/api/catalog/books \
  -H "Content-Type: application/json" \
  -d '{
    "isbn": "9780137081073",
    "title": "The Clean Coder",
    "authorName": "Robert C. Martin",
    "category": "Programming",
    "totalQuantity": 2
  }'

# 2. Cadastrar novo usuário
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Carlos Mendes",
    "email": "carlos.mendes@email.com",
    "cpf": "55566677788",
    "userType": "COMMON"
  }'
```

---

## Solução de Problemas

### Porta 8080 já está em uso

Edite `src/main/resources/application.properties` e mude a porta:

```properties
server.port=8081
```

### Banco de dados corrompido

Exclua a pasta `data/` e reinicie a aplicação. O banco será recriado com dados iniciais.

### Erro de permissão no Maven Wrapper (Linux/Mac)

```bash
chmod +x mvnw
```

### Logs muito verbosos

Edite `src/main/resources/application.properties`:

```properties
logging.level.root=WARN
logging.level.com.biblioteca=INFO
```
