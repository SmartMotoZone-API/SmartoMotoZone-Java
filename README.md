---

# 🛵 SmartMotoZone API

API RESTful desenvolvida com **Java Spring Boot** para rastreamento e gerenciamento de motos em zonas virtuais, simulando o uso de **beacons** em pátios logísticos.

---

## 🎯 Objetivo

Facilitar o monitoramento e organização de motos em ambientes logísticos, permitindo o controle de zonas virtuais (beacons) associadas a cada veículo.

---

## 🧩 Funcionalidades

✅ CRUD completo de motos, zonas, usuários, funcionários e movimentações

✅ Relacionamento entre entidades (`Moto` pertence a uma `Zona`)

✅ Paginação e ordenação nas listagens

✅ Filtros específicos: motos por modelo ou zona, zonas por descrição, usuários por login

✅ Validações robustas com Bean Validation

✅ Cache para otimizar consultas frequentes

✅ DTOs para comunicação segura e eficiente

✅ Tratamento global de exceções com mensagens claras



---

## 🛠 Tecnologias Utilizadas

* Java 17
* Spring Boot 3.x
* Spring Web
* Spring Data JPA
* H2 Database (em memória para desenvolvimento)
* Spring Cache (cache distribuído local)
* Bean Validation (Jakarta)
* Lombok

---

## 📦 Estrutura do Projeto

```
smartmotozone/
├── controller/
├── dto/
├── exception/
├── model/
├── repository/
├── service/
├── SmartMotoZoneApiApplication.java
└── resources/
    ├── application.properties
    └── data.sql
```

---

## 🧱 Entidades

### 🟦 Zona

| Campo     | Tipo   | Descrição                       |
| --------- | ------ | ------------------------------- |
| id        | Long   | ID da zona                      |
| codigo    | String | Código identificador (ex: Z001) |
| descricao | String | Descrição da zona               |

### 🏍 Moto

| Campo  | Tipo   | Descrição                                           |
| ------ | ------ | --------------------------------------------------- |
| id     | Long   | ID da moto                                          |
| modelo | String | Modelo da moto (**obrigatório**)                    |
| placa  | String | Placa da moto (**obrigatória**)                     |
| status | String | Status da moto (ex: Ativa) (**obrigatório**)        |
| zona   | Zona   | Zona à qual a moto está vinculada (**obrigatória**) |

### 👤 Usuário

| Campo  | Tipo   | Descrição                                     |
| ------ | ------ | --------------------------------------------- |
| id     | Long   | Identificador único do usuário                |
| nome   | String | Nome completo                                 |
| perfil | String | Perfil do usuário (ex: ADMIN, USUARIO)        |
| login  | String | Login único para autenticação                 |
| senha  | String | Senha do usuário (armazenada de forma segura) |
| email  | String | Email de contato                              |

### 👷 Funcionário

| Campo | Tipo   | Descrição                          |
| ----- | ------ | ---------------------------------- |
| id    | Long   | Identificador único do funcionário |
| nome  | String | Nome completo                      |
| cargo | String | Cargo ou função dentro da empresa  |

### 🔄 Movimentação

| Campo       | Tipo          | Descrição                                        |
| ----------- | ------------- | ------------------------------------------------ |
| id          | Long          | Identificador único da movimentação              |
| moto        | Moto          | Moto associada à movimentação                    |
| zonaOrigem  | Zona          | Zona de origem da movimentação                   |
| zonaDestino | Zona          | Zona destino da movimentação                     |
| descricao   | String        | Descrição da movimentação                        |
| dataHora    | LocalDateTime | Data e hora em que a movimentação foi registrada |

---

## 📄 Endpoints Principais

### 🚗 Motos

| Método | Caminho       | Descrição                                      |
| ------ | ------------- | ---------------------------------------------- |
| POST   | `/motos`      | Cadastrar uma nova moto                        |
| GET    | `/motos`      | Listar motos com paginação e filtro por modelo |
| GET    | `/motos/zona` | Listar motos por código da zona (com cache)    |
| GET    | `/motos/{id}` | Buscar moto por ID                             |
| PUT    | `/motos/{id}` | Atualizar dados de uma moto                    |
| DELETE | `/motos/{id}` | Excluir uma moto                               |

### 📍 Zonas

| Método | Caminho                    | Descrição                  |
| ------ | -------------------------- | -------------------------- |
| POST   | `/zonas`                   | Criar uma nova zona        |
| GET    | `/zonas`                   | Listar todas as zonas      |
| GET    | `/zonas/{id}`              | Buscar zona por ID         |
| PUT    | `/zonas/{id}`              | Atualizar uma zona         |
| DELETE | `/zonas/{id}`              | Excluir uma zona           |
| GET    | `/zonas/buscar?descricao=` | Buscar zonas por descrição |

### 👤 Usuários

| Método | Caminho                   | Descrição                     |
| ------ | ------------------------- | ----------------------------- |
| POST   | `/usuarios`               | Cadastrar um novo usuário     |
| GET    | `/usuarios`               | Listar usuários com paginação |
| GET    | `/usuarios/{id}`          | Buscar usuário por ID         |
| GET    | `/usuarios/login/{login}` | Buscar usuário por login      |
| PUT    | `/usuarios/{id}`          | Atualizar usuário             |
| DELETE | `/usuarios/{id}`          | Excluir usuário               |

### 👷 Funcionários

| Método | Caminho              | Descrição                         |
| ------ | -------------------- | --------------------------------- |
| POST   | `/funcionarios`      | Cadastrar um novo funcionário     |
| GET    | `/funcionarios`      | Listar funcionários com paginação |
| GET    | `/funcionarios/{id}` | Buscar funcionário por ID         |
| PUT    | `/funcionarios/{id}` | Atualizar funcionário             |
| DELETE | `/funcionarios/{id}` | Excluir funcionário               |

### 🔄 Movimentações

| Método | Caminho                        | Descrição                                   |
| ------ | ------------------------------ | ------------------------------------------- |
| POST   | `/movimentacoes`               | Registrar nova movimentação                 |
| GET    | `/movimentacoes`               | Listar movimentações com paginação          |
| GET    | `/movimentacoes/moto/{motoId}` | Buscar movimentações por moto com paginação |

---

## 🔐 Validações

Utiliza `Jakarta Bean Validation` para garantir consistência dos dados, por exemplo:

```java
@NotBlank
@Size(min = 2, max = 30)
private String modelo;
```

---

## 💥 Tratamento de Erros

Exceções são tratadas de forma centralizada usando `@ControllerAdvice`, retornando respostas padronizadas para o cliente em casos de erro de validação, dados não encontrados ou violações de regras de negócio.

---

## 🧪 Como Executar

1. **Clone o projeto:**

```bash
git clone https://github.com/SmartMotoZone-API/smart-moto-zone-api.git
cd smartmotozone
```

2. **Execute com Maven:**

```bash
./mvnw spring-boot:run
```

3. **Acesse os recursos no navegador:**

* H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

---

## 💬 Exemplos de JSON (POST Requests)

### Cadastrar Moto

```json
{
  "modelo": "CG 160 Fan",
  "placa": "ABC1234",
  "status": "ativa",
  "zonaId": 1
}
```

### Cadastrar Zona

```json
{
  "codigo": "Z001",
  "descricao": "Zona de Entrada Principal"
}
```

### Cadastrar Usuário

```json
{
  "nome": "João Silva",
  "perfil": "ADMIN",
  "login": "joaos",
  "senha": "senha123",
  "email": "joao.silva@email.com"
}
```

### Cadastrar Funcionário

```json
{
  "nome": "Maria Souza",
  "cargo": "Operadora de Pátio"
}
```

### Registrar Movimentação

```json
{
  "motoId": 1,
  "zonaOrigemId": 1,
  "zonaDestinoId": 2,
  "descricao": "Movimentação para zona de manutenção",
  "dataHora": "2025-05-22T14:30:00"
}
```

---

## 👨‍💻 Autores

| Nome Completo      | RM     | GitHub                           |
| ------------------ | ------ | -------------------------------- |
| \[Kaio Cumpian Silva] | \[99816] | [https://github.com/KaioCumpian](#) |
| \[Lucas Felix Vassiliades] | \[97677] | |
| \[Gabriel Yuji Suzuki] | \[556588] | |

---

## 🧠 Observações Finais

Este projeto foi desenvolvido como parte de um desafio acadêmico focado em boas práticas de arquitetura REST, uso eficiente do Spring Boot, DTOs, validação, cache e tratamento de exceções. O objetivo principal é demonstrar domínio dos conceitos de desenvolvimento backend moderno para aplicações corporativas.

---

