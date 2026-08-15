# TM Evolut Ecommerce API

API REST de um sistema de e-commerce desenvolvida com Java e Spring Boot como projeto acadêmico.

O sistema disponibiliza recursos para gerenciamento de **categorias, produtos, clientes e pedidos**, utilizando uma arquitetura organizada em camadas e persistência de dados com JPA/Hibernate.

---

## 👥 Integrantes

* Marcos Nascimento
* Herbert Taylor
* Victor Albuquerque

**Professor:** Francisco Erberto
**Instituição:** UNIBRA — Centro Universitário Brasileiro

---

## 🎯 Objetivo do projeto

O projeto tem como objetivo desenvolver uma API REST para gerenciamento de recursos de um sistema de e-commerce.

A aplicação permite realizar operações relacionadas a:

* Categorias;
* Produtos;
* Clientes;
* Pedidos;
* Itens de pedidos.

Além disso, o projeto utiliza validação de dados, tratamento global de exceções, documentação da API com Swagger/OpenAPI e testes automatizados.

---

## 🛠️ Tecnologias utilizadas

* **Java 17**
* **Spring Boot 3.4.3**
* **Spring Web**
* **Spring Data JPA**
* **Hibernate**
* **Bean Validation**
* **H2 Database**
* **Swagger / OpenAPI**
* **JUnit**
* **Mockito**
* **JaCoCo**
* **Maven**
* **Git / GitHub**

---

## 🏗️ Estrutura do projeto

A aplicação está organizada em camadas para separar as responsabilidades do sistema.

```text
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── tmevolut/
│   │           └── ecommerce/
│   │               └── api/
│   │                   ├── controller/
│   │                   ├── dto/
│   │                   ├── entity/
│   │                   ├── exception/
│   │                   ├── repository/
│   │                   ├── service/
│   │                   ├── validator/
│   │                   └── TmEvolutEcommerceApplication.java
│   │
│   └── resources/
│       └── application.properties
│
└── test/
    └── java/
        └── com/
            └── tmevolut/
                └── ecommerce/
                    └── api/
```

### Principais pacotes

#### `controller`

Responsável pelos endpoints da API REST e pelo recebimento das requisições HTTP.

Entre os controllers existentes estão:

* `CategoriaController`
* `ClienteController`
* `PedidoController`
* `ProdutoController`
* `RootController`

#### `dto`

Contém os objetos utilizados para transferência de dados entre a API e seus consumidores.

#### `entity`

Contém as entidades utilizadas na persistência dos dados.

Entre elas:

* `Categoria`
* `Cliente`
* `ItemPedido`
* `Pedido`
* `Produto`
* `StatusPedido`

#### `exception`

Responsável pelo tratamento das exceções da aplicação.

Entre as classes existentes estão:

* `BusinessException`
* `ResourceNotFoundException`
* `GlobalExceptionHandler`
* `ErrorResponse`

#### `repository`

Responsável pelo acesso aos dados através do Spring Data JPA.

#### `service`

Contém as regras e operações de negócio da aplicação.

Entre os serviços existentes estão:

* `CategoriaService`
* `ClienteService`
* `PedidoService`
* `ProdutoService`

#### `validator`

Contém validações específicas utilizadas pela aplicação, incluindo a validação de SKU.

---

## 📦 Principais funcionalidades

### Categorias

A API permite:

* Listar categorias;
* Consultar categoria por ID;
* Cadastrar categoria;
* Atualizar categoria;
* Excluir categoria.

Base dos endpoints:

```text
/api/v1/categorias
```

---

### Produtos

A API permite:

* Listar produtos;
* Consultar produto por ID;
* Cadastrar produto;
* Atualizar produto;
* Alterar parcialmente um produto;
* Excluir produto;
* Realizar consulta utilizando parâmetros de filtro;
* Utilizar paginação na consulta de produtos.

Base dos endpoints:

```text
/api/v1/produtos
```

---

### Clientes

A API possui recursos para gerenciamento de clientes através dos endpoints disponibilizados pelo `ClienteController`.

---

### Pedidos

A API possui recursos relacionados ao gerenciamento de pedidos e seus itens.

O projeto também possui o enum `StatusPedido`, utilizado para representar os estados dos pedidos.

---

## 🔗 API REST

Os endpoints da aplicação seguem o padrão REST e utilizam JSON para troca de informações.

As principais operações HTTP utilizadas são:

* `GET` — consulta de informações;
* `POST` — criação de recursos;
* `PUT` — atualização de recursos;
* `PATCH` — atualização parcial de recursos;
* `DELETE` — exclusão de recursos.

---

## ✅ Validação

A aplicação utiliza Bean Validation para validação dos dados recebidos pelas requisições.

Também existe uma validação específica para SKU através das classes:

```text
SkuValido
SkuValidoValidator
```

---

## ⚠️ Tratamento de exceções

A aplicação possui tratamento global de exceções através da classe:

```text
GlobalExceptionHandler
```

Também são utilizadas exceções específicas, como:

```text
BusinessException
ResourceNotFoundException
```

As respostas de erro são estruturadas através da classe:

```text
ErrorResponse
```

---

## 📚 Documentação da API

A aplicação utiliza **Springdoc OpenAPI** para documentação dos endpoints.

Após iniciar a aplicação, a interface do Swagger pode ser acessada em:

```text
http://localhost:8080/swagger-ui.html
```

A especificação OpenAPI também pode ser acessada em:

```text
http://localhost:8080/v3/api-docs
```

---

## 🗄️ Banco de dados

Durante o desenvolvimento, o projeto utiliza o banco de dados **H2 em memória**.

Configuração utilizada:

```text
JDBC URL:
jdbc:h2:mem:ecommerce

Usuário:
sa

Senha:
```

A senha utilizada é vazia.

---

## 🖥️ Console H2

O console do H2 está habilitado para facilitar o acesso ao banco durante o desenvolvimento.

Após iniciar a aplicação, acesse:

```text
http://localhost:8080/h2-console
```

Utilize:

```text
JDBC URL: jdbc:h2:mem:ecommerce
User Name: sa
Password:
```

---

## ▶️ Como executar o projeto

### Pré-requisitos

Para executar o projeto, é necessário possuir:

* Java 17;
* Git;
* Maven ou utilizar o Maven Wrapper disponibilizado no projeto.

### Clonar o repositório

```bash
git clone https://github.com/MarcosNasc-DEV/TM-evolut-ecommerce-api.git
```

Depois, entre na pasta do projeto:

```bash
cd TM-evolut-ecommerce-api
```

### Executar utilizando Maven Wrapper

No Windows:

```bash
mvnw.cmd spring-boot:run
```

No Linux/macOS:

```bash
./mvnw spring-boot:run
```

---

## 🧪 Testes

O projeto possui testes automatizados utilizando JUnit e Mockito.

Os testes estão organizados principalmente nas camadas de:

* Controllers;
* Services.

Entre os testes existentes estão:

```text
CategoriaControllerTest
ClienteControllerTest
PedidoControllerTest
ProdutoControllerTest
RootControllerTest
```

e testes dos serviços:

```text
CategoriaServiceTest
ClienteServiceTest
PedidoServiceTest
ProdutoServiceTest
```

Para executar os testes utilizando Maven:

### Windows

```bash
mvnw.cmd test
```

### Linux/macOS

```bash
./mvnw test
```

---

## 📊 Cobertura de testes

O projeto utiliza o **JaCoCo** para geração e verificação da cobertura dos testes.

A configuração do projeto estabelece critérios mínimos de cobertura para branches e instructions.

O relatório de cobertura pode ser gerado através da execução dos comandos Maven configurados no projeto.

---

## 📁 Principais recursos da aplicação

```text
Categorias
    ├── Cadastro
    ├── Consulta
    ├── Atualização
    └── Exclusão

Produtos
    ├── Cadastro
    ├── Consulta
    ├── Atualização
    ├── Atualização parcial
    ├── Exclusão
    ├── Filtros
    └── Paginação

Clientes
    └── Gerenciamento de clientes

Pedidos
    └── Gerenciamento de pedidos e itens

Validação
    └── Bean Validation
    └── Validação de SKU

Tratamento de erros
    └── GlobalExceptionHandler

Documentação
    └── Swagger/OpenAPI

Banco de dados
    └── H2

Testes
    └── JUnit
    └── Mockito
    └── JaCoCo
```

---

## 📌 Observações

O banco H2 utilizado pela aplicação é configurado em memória. Dessa forma, os dados armazenados durante a execução são destinados ao ambiente de desenvolvimento e são perdidos quando a aplicação é encerrada.

A aplicação foi desenvolvida com foco acadêmico e utiliza uma arquitetura baseada na separação de responsabilidades entre controllers, services, repositories, entities, DTOs, validações e tratamento de exceções.

---

## 📄 Licença

Projeto desenvolvido para fins acadêmicos.
