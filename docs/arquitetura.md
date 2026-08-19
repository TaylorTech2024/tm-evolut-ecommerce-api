# Arquitetura do Projeto

O projeto TM Evolut Ecommerce API utiliza uma arquitetura organizada em camadas, separando as responsabilidades da aplicação.

## Fluxo da aplicação

```text
Cliente
   ↓
Controller
   ↓
DTO
   ↓
Service
   ↓
Repository
   ↓
Entity
   ↓
Banco de Dados H2
```

## Estrutura dos pacotes

### Controller

Responsável por disponibilizar os endpoints da API REST e receber as requisições HTTP.

Principais controllers:

* CategoriaController
* ClienteController
* PedidoController
* ProdutoController
* RootController

### DTO

Responsável pelos objetos utilizados para transferência de dados entre as requisições, respostas e as demais camadas da aplicação.

### Entity

Contém as entidades utilizadas pela aplicação e pelo processo de persistência dos dados.

Entre as principais entidades estão:

* Categoria
* Cliente
* ItemPedido
* Pedido
* Produto
* StatusPedido

### Service

Responsável pelas operações e regras de negócio da aplicação.

Principais services:

* CategoriaService
* ClienteService
* PedidoService
* ProdutoService

### Repository

Responsável pelo acesso e persistência dos dados utilizando Spring Data JPA.

### Exception

Responsável pelo tratamento das exceções da aplicação.

Entre as classes existentes estão:

* BusinessException
* ResourceNotFoundException
* GlobalExceptionHandler
* ErrorResponse

### Validator

Responsável por validações específicas da aplicação, incluindo a validação de SKU.

## Banco de Dados

Durante o desenvolvimento, a aplicação utiliza o banco de dados H2 em memória.

A configuração utilizada está definida no arquivo `application.properties`.
