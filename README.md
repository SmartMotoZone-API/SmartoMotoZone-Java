# 🛵 SmartMotoZone API

API RESTful desenvolvida com **Java Spring Boot** para rastreamento e gerenciamento de motos em zonas virtuais, simulando o uso de **beacons** em pátios logísticos.

---

## 🎯 Objetivo

Facilitar o monitoramento e organização de motos em ambientes logísticos, permitindo o controle de zonas virtuais (beacons) associadas a cada veículo.

---

## 🧩 Funcionalidades

✅ CRUD completo de motos e zonas  
✅ Relacionamento entre entidades (`Moto` pertence a uma `Zona`)  
✅ Paginação e ordenação nas listagens  
✅ Filtro de motos por modelo ou zona  
✅ Validações com Bean Validation  
✅ Cache para listagens por zona  
✅ DTOs para comunicação segura e eficiente  
✅ Tratamento global de exceções  
✅ Integração com Swagger para documentação

---

## 🛠 Tecnologias Utilizadas

- Java 17
- Spring Boot 3.x
- Spring Web
- Spring Data JPA
- H2 Database (em memória)
- Spring Cache
- Bean Validation (Jakarta)
- Lombok
- Swagger/OpenAPI (springdoc-openapi-ui)

---

## 📦 Estrutura do Projeto

text
smartmotozone/
├── controller/
├── dto/
├── exception/
├── model/
├── repository/
├── service/
├── SmartMotoZoneApiApplication.java
└── resources/
    └── application.properties
`

---

## 🧱 Entidades

### 🟦 Zona

| Campo     | Tipo   | Descrição                       |
| --------- | ------ | ------------------------------- |
| id        | Long   | ID da zona                      |
| codigo    | String | Código identificador (ex: Z001) |
| descricao | String | Descrição da zona               |

### 🏍 Moto

| Campo  | Tipo   | Descrição                         |
| ------ | ------ | --------------------------------- |
| id     | Long   | ID da moto                        |
| modelo | String | Modelo da moto                    |
| status | String | Status da moto (ex: Ativa)        |
| zona   | Zona   | Zona à qual a moto está vinculada |

---

## 📄 Endpoints Principais

### 🚗 Motos

| Método | Caminho       | Descrição                                      |
| ------ | ------------- | ---------------------------------------------- |
| POST   | `/motos`      | Cadastrar uma nova moto                        |
| GET    | `/motos`      | Listar motos com paginação e filtro por modelo |
| GET    | `/motos/zona` | Listar motos por código da zona (com cache)    |

### 📍 Zonas

| Método | Caminho  | Descrição             |
| ------ | -------- | --------------------- |
| POST   | `/zonas` | Criar uma nova zona   |
| GET    | `/zonas` | Listar todas as zonas |

---

## 🔐 Validações

Utiliza `Jakarta Bean Validation` para garantir consistência dos dados, por exemplo:

java
@NotBlank
@Size(min = 2, max = 30)
private String modelo;


---

## 💥 Tratamento de Erros

Exceções são tratadas de forma centralizada usando `@ControllerAdvice`, retornando respostas padronizadas para o cliente em casos de erro de validação ou problemas na API.

---

## 🧪 Como Executar

1. **Clone o projeto:**

bash
git clone https://github.com/SmartMotoZone-API/smart-moto-zone-api.git
cd smartmotozone


2. **Execute com Maven:**

bash
./mvnw spring-boot:run


3. **Acesse os recursos no navegador:**

* Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
* H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

---

## 💬 Exemplo de JSON (POST /motos)

json
{
  "modelo": "CG 160 Fan",
  "status": "ativa",
  "zonaCodigo": "Z001"
}


---

## 👨‍💻 Autor

**Kaio Cumpian Silva**
FIAP – Java Advanced
GitHub: [https://github.com/KaioCumpian](https://github.com/KaioCumpian)

---

## 🧠 Observação

Este projeto é parte de um desafio acadêmico com foco em boas práticas de arquitetura, REST, DTOs, validação e uso eficiente dos recursos do Spring Boot.



---
