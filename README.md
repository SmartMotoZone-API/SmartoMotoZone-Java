

# 🛵 SmartMotoZone - Aplicação Web e API

Solução completa desenvolvida com **Java e Spring Boot** para o desafio de rastreamento e gerenciamento de motos em pátios logísticos.

Este projeto implementa uma arquitetura robusta que serve tanto uma **API RESTful** (para consumo mobile) quanto uma **Aplicação Web** (para gestão interna), cumprindo todos os requisitos das Sprints de Java Advanced.

-----

## 🎯 Objetivo

Facilitar o monitoramento e organização de motos em ambientes logísticos, permitindo o controle de zonas virtuais (beacons) associadas a cada veículo, com autenticação e controlo de acesso por perfis.

-----

## 🧩 Funcionalidades

  * **API RESTful Completa:** Endpoints CRUD para todas as entidades (Motos, Zonas, Usuários, etc.).
  * **Frontend Web (Thymeleaf):** Interface de gestão interna com formulários e listagens.
  * **Segurança Dupla (Spring Security):**
      * **API (Stateless):** Protegida por **JWT (Tokens)** para consumo seguro por aplicações mobile.
      * **Web (Stateful):** Protegida por **Formulário de Login e Sessões**.
  * **Controlo de Acesso por Perfil (RBAC):**
      * **ADMIN:** Acesso total ao CRUD web e API.
      * **USUARIO:** Acesso de apenas leitura no site web e registo público.
  * **Migrações de Base de Dados:** Gestão de schema automatizada com **Flyway**.
  * **Boas Práticas:** Validação (DTOs), tratamento de exceções (`@RestControllerAdvice`), e Caching.

-----

## 🛠 Tecnologias Utilizadas

  * Java 21
  * Spring Boot 3.3.0
  * Spring Data JPA
  * Spring Security 6
  * **Thymeleaf** (com `thymeleaf-extras-springsecurity6`)
  * **PostgreSQL** (Base de dados de produção)
  * **Flyway** (Migrações de Base de Dados)
  * **JWT (JSON Web Tokens)** (para a API)
  * Spring Cache (cache local)
  * Lombok
  * Maven

-----

## 🚀 Como Executar (Local)

1.  **Clone o projeto:**

    ```bash
    git clone https://github.com/SEU-USUARIO/SEU-REPOSITORIO.git
    cd SmartoMotoZone-Java
    ```

2.  **Configure a Base de Dados (PostgreSQL):**
    Este projeto usa o Flyway, que criará automaticamente todas as tabelas ao arrancar.
    Você **precisa** de fornecer as credenciais da sua base de dados PostgreSQL através de variáveis de ambiente.

3.  **Configure as Variáveis de Ambiente:**
    O ficheiro `application.properties` espera as seguintes variáveis:

    ```
    DB_URL=jdbc:postgresql://<seu-host>:<sua-porta>/<seu-banco>
    DB_USERNAME=<seu-usuario-db>
    DB_PASSWORD=<sua-senha-db>
    ```

    *(No IntelliJ, pode configurar isto em "Run" \> "Edit Configurations..." \> "Environment variables").*

4.  **Execute com Maven:**

    ```bash
    ./mvnw spring-boot:run
    ```

-----

## 🖥️ Acesso à Aplicação Web

A aplicação web (Thymeleaf) fica disponível em `http://localhost:8080/`.

  * **Página de Login:** `http://localhost:8080/web/login`
  * **Página de Registo:** `http://localhost:8080/web/register` (Cria um perfil `USUARIO`)

### Perfis de Acesso

  * **ADMIN (Administrador):**

      * **Login:** `admin`
      * **Senha:** `admin` (ou a senha que você definiu no seu `UPDATE` manual)
      * **Acesso:** Pode ver, criar, editar e apagar todas as entidades (Motos, Zonas, Usuários, etc.).

  * **USUARIO (Utilizador Padrão):**

      * **Login:** Crie uma nova conta na página de registo.
      * **Acesso:** Pode ver as listas de Motos, Zonas e Movimentações, mas não pode ver os botões de "Novo", "Editar" ou "Deletar".


-----

## 👨‍💻 Autores

| Nome Completo | RM |
| :--- | :--- | :--- |
| Luiz Eduardo Da Silva Pinto | 555213 |
| Lucas Felix Vassiliades | 97677 |
| Gabriel Yuji Suzuki | 556588 | 