# Biblioteca Digital

Sistema de gestão de biblioteca com Domain-Driven Design (DDD), SOLID e arquitetura preparada para microserviços.

---

## Pré-requisitos

| Ferramenta | Versão mínima | Verificar |
|---|---|---|
| Docker + Compose | 20+ | `docker --version` |
| kubectl + cluster | 1.25+ | `kubectl version` |
| Helm | 3+ | `helm version` |

---

## Docker Compose

Stack: **App** · **Nginx** · **Prometheus** · **Grafana**

```bash
git clone https://github.com/angelo-giordano/ada-java.git && cd ada-java/docker
```

O Nginx requer um certificado SSL. Gere um certificado autoassinado antes de subir os serviços:

```bash
openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
  -keyout nginx/certs/biblioteca-ada.com.key \
  -out nginx/certs/biblioteca-ada.com.crt \
  -subj "/CN=biblioteca-ada.com"
```

```bash
docker compose up -d
```

| Serviço | URL |
|---|---|
| API | https://localhost/api/health |
| H2 Console | https://localhost/h2-console |
| Grafana | http://localhost:3000 |
| Prometheus | http://localhost:9090 |

> **H2 Console:** JDBC URL `jdbc:h2:file:./data/biblioteca` · User: `sa` · Password: *(vazio)*

```bash
docker compose down
```

---

## Kubernetes

Stack: **App** · **Envoy Gateway** (Gateway API) · **Prometheus** · **Grafana** · **Loki**

### 1. Instalar dependências

```bash
# Stack de monitoramento (Prometheus, Grafana, Loki)
helm install eg-addons oci://docker.io/envoyproxy/gateway-addons-helm \
  --version v0.0.0-latest \
  -n monitoring --create-namespace

# Envoy Gateway
helm install eg oci://docker.io/envoyproxy/gateway-helm \
  --version v1.7.0 \
  -n envoy-gateway-system --create-namespace

# Desabilitar Tempo (tracing, opcional)
helm upgrade eg-addons oci://docker.io/envoyproxy/gateway-addons-helm \
  --version v0.0.0-latest \
  --reuse-values \
  --set tempo.enabled=false \
  -n monitoring
```

### 2. Configurar Prometheus

Adicionar o job da API ao ConfigMap do Prometheus:

```bash
kubectl edit cm prometheus -n monitoring
```

```yaml
- job_name: 'api'
  metrics_path: '/actuator/prometheus'
  static_configs:
    - targets: ['biblioteca-svc.biblioteca.svc.cluster.local:80']
```

### 3. Criar Secrets

**Certificado TLS** (usado pelo Gateway no listener HTTPS):

```bash
openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
  -keyout biblioteca-ada.com.key \
  -out biblioteca-ada.com.crt \
  -subj "/CN=biblioteca-ada.com"

kubectl create namespace certs
kubectl create secret tls my-tls-cert \
  --cert=biblioteca-ada.com.crt \
  --key=biblioteca-ada.com.key \
  -n certs
```

**Basic Auth** (protege o `/actuator` via SecurityPolicy):

```bash
htpasswd -cb auth <usuario> <senha>

kubectl create secret generic basic-auth \
  --from-file=credentials=auth \
  -n biblioteca
```

### 4. Aplicar a infraestrutura

```bash
kubectl apply -f k8s/

# Verificar pods
kubectl get pods -n biblioteca

# Obter endereço do Gateway
kubectl get gateway -n biblioteca
```

### 5. Configurar acesso local

A aplicação é exposta via NodePort em `https://biblioteca-ada.com:<PORTA-DO-NODE>`. Adicione ao `/etc/hosts`:

```
<IP-DO-NODE> biblioteca-ada.com
```

Para remover tudo: `kubectl delete -f k8s/`

---

## Testes

Os scripts estão em `scripts/docker/` e `scripts/k8s/`.

> Os scripts do Kubernetes usam `https://biblioteca-ada.com:30821/` como host. Os do Docker usam `https://localhost/`.

| Script | O que testa | Comprova |
|---|---|---|
| `requests.sh` | Volume de requisições | Funcionamento geral da API |
| `rate-limit.sh <endpoint> <n>` | Rajada de `n` requisições | `HTTP 429` após estouro do burst |
| `payload-size.sh` | Upload acima de 1 MB | `HTTP 413` |
| `encoding.sh` | Header de compressão | `Content-Encoding: gzip` |
| `cache.sh` | Cache de GET | Hit/miss em respostas consecutivas (apenas Docker) |

### Exemplos

```bash
# Docker — disparar 20 requisições e verificar 429
bash scripts/docker/rate-limit.sh api/catalog/books 20

# Kubernetes
bash scripts/k8s/rate-limit.sh api/catalog/books 20
```

---

## API — Referência Rápida

**Base URL:** `https://localhost/api` (Docker) · `https://biblioteca-ada.com:30821/api` (Kubernetes)

### Catálogo de Livros

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/catalog/books` | Listar livros (`?availableOnly=true`) |
| GET | `/catalog/books/{id}` | Buscar por ID |
| GET | `/catalog/books/search?q=` | Buscar por título/autor |
| POST | `/catalog/books` | Cadastrar livro |

**Cadastrar livro:**
```json
{ "isbn": "9780134685991", "title": "Effective Java", "authorName": "Joshua Bloch", "category": "Programming", "totalQuantity": 3 }
```

### Usuários

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/users` | Listar usuários |
| GET | `/users/{id}` | Buscar por ID |
| POST | `/users` | Cadastrar usuário |

**Tipos de usuário:**

| Tipo | Livros simultâneos | Prazo |
|---|---|---|
| `STUDENT` | 3 | 14 dias |
| `PROFESSOR` | 5 | 30 dias |
| `COMMON` | 2 | 7 dias |

### Empréstimos

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/lending/loans` | Emprestar livro |
| PUT | `/lending/loans/{id}/return` | Devolver livro |
| GET | `/lending/loans/user/{userId}` | Empréstimos ativos do usuário |

> **Multa por atraso:** R$ 2,00/dia

---

## Dados de Exemplo

**Livros:** Clean Code · Domain-Driven Design · Design Patterns · Effective Java · Head First Design Patterns

**Usuários:** João Silva (`user-001`, STUDENT) · Maria Santos (`user-002`, PROFESSOR) · Pedro Oliveira (`user-003`, COMMON)
