# TM Evolut Ecommerce

API REST de e-commerce desenvolvida com Spring Boot.

Projeto acadêmico da disciplina Cliente-Servidor.

## Integrantes

- Herbert Taylor
- Marcos Nascimento
- Victor Albuquerque

## Professor

Francisco Ereberto

## Instituição

UNIBRA — Centro Universitário Brasileiro

---

## Objetivo do Projeto

Desenvolver uma API REST para simular o funcionamento de uma loja virtual, permitindo o gerenciamento de categorias, produtos, clientes e pedidos, aplicando os conceitos de arquitetura cliente-servidor, persistência de dados, validações, documentação e testes automatizados.

---

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Maven
- Spring Data JPA
- Hibernate
- H2 Database
- Swagger / OpenAPI
- JUnit
- Mockito
- JaCoCo
- Git e GitHub

---

## Estrutura do Projeto

```txt
src/main/java/com/tmevolut/ecommerce/api

├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── service
└── validator
```

---

## Como executar o projeto

### Compilar

```bash
./mvnw clean install
```

### Executar

```bash
./mvnw spring-boot:run
```

---

## Swagger

```txt
http://localhost:8080/swagger-ui.html
```

---

## Banco H2

```txt
http://localhost:8080/h2-console
```

JDBC URL:

```txt
jdbc:h2:mem:ecommerce
```

Usuário:

```txt
sa
```

Senha:

```txt
(em branco)
```
