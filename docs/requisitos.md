# Requisitos do Projeto

## Requisitos Funcionais

Os requisitos funcionais abaixo foram definidos para o sistema de e-commerce e foram relacionados às funcionalidades implementadas na API.

| ID   | Requisito                  | Aplicação                                        | Situação     |
| ---- | -------------------------- | ------------------------------------------------ | ------------ |
| RF01 | Cadastrar categorias       | Permitir criação de categorias de produtos.      | Implementado |
| RF02 | Listar categorias          | Exibir todas as categorias cadastradas.          | Implementado |
| RF03 | Atualizar categorias       | Editar informações de categorias existentes.     | Implementado |
| RF04 | Excluir categorias         | Remover categorias do sistema.                   | Implementado |
| RF05 | Cadastrar produtos         | Registrar novos produtos no sistema.             | Implementado |
| RF06 | Listar produtos            | Mostrar todos os produtos cadastrados.           | Implementado |
| RF07 | Buscar produto por ID      | Consultar produto específico pelo identificador. | Implementado |
| RF08 | Atualizar produtos         | Alterar informações de produtos.                 | Implementado |
| RF09 | Excluir produtos           | Remover produtos do sistema.                     | Implementado |
| RF10 | Cadastrar clientes         | Registrar novos clientes.                        | Implementado |
| RF11 | Listar clientes            | Exibir clientes cadastrados.                     | Implementado |
| RF12 | Atualizar clientes         | Modificar dados dos clientes.                    | Implementado |
| RF13 | Excluir clientes           | Excluir clientes cadastrados.                    | Implementado |
| RF14 | Criar pedidos              | Permitir criação de pedidos de compra.           | Implementado |
| RF15 | Listar pedidos             | Mostrar todos os pedidos realizados.             | Implementado |
| RF16 | Buscar pedido por ID       | Consultar pedido específico.                     | Implementado |
| RF17 | Atualizar status do pedido | Alterar status do pedido.                        | Implementado |
| RF18 | Cancelar pedido            | Permitir cancelamento de pedidos.                | Implementado |

---

## Requisitos Não Funcionais

| ID    | Requisito                     | Aplicação                                                                 | Situação     |
| ----- | ----------------------------- | ------------------------------------------------------------------------- | ------------ |
| RNF01 | Java 17                       | A aplicação deve ser desenvolvida utilizando Java 17.                     | Implementado |
| RNF02 | Spring Boot                   | Utilização do framework Spring Boot para desenvolvimento da aplicação.    | Implementado |
| RNF03 | Banco de dados H2             | Utilização do H2 como banco de dados durante o desenvolvimento.           | Implementado |
| RNF04 | Arquitetura MVC               | Organização da aplicação seguindo o padrão arquitetural MVC.              | Implementado |
| RNF05 | API REST                      | A aplicação deve disponibilizar seus recursos por meio de uma API REST.   | Implementado |
| RNF06 | JSON                          | Utilização do formato JSON na comunicação entre cliente e API.            | Implementado |
| RNF07 | Bean Validation               | Utilização de validações para garantir a integridade dos dados recebidos. | Implementado |
| RNF08 | Tratamento global de exceções | A aplicação deve possuir tratamento centralizado das exceções.            | Implementado |
| RNF09 | Swagger/OpenAPI               | A API deve possuir documentação dos endpoints utilizando Swagger/OpenAPI. | Implementado |
| RNF10 | Testes com JUnit e Mockito    | A aplicação deve possuir testes automatizados utilizando JUnit e Mockito. | Implementado |

---

## Implementação dos Requisitos Funcionais

### Categorias

Os requisitos RF01 a RF04 são atendidos pelo `CategoriaController`, que disponibiliza operações para:

* Cadastrar categorias;
* Listar categorias;
* Buscar categoria por ID;
* Atualizar categorias;
* Excluir categorias.

Endpoint base:

```text
/api/v1/categorias
```

### Produtos

Os requisitos RF05 a RF09 são atendidos pelo `ProdutoController`, que disponibiliza operações para:

* Cadastrar produtos;
* Listar produtos;
* Buscar produto por ID;
* Atualizar produtos;
* Atualizar produtos parcialmente;
* Excluir produtos.

Endpoint base:

```text
/api/v1/produtos
```

A listagem de produtos também possui recursos de filtro e paginação.

### Clientes

Os requisitos RF10 a RF13 são atendidos pelo `ClienteController`, que disponibiliza operações para:

* Cadastrar clientes;
* Listar clientes;
* Buscar cliente por ID;
* Atualizar clientes;
* Excluir clientes.

Endpoint base:

```text
/api/v1/clientes
```

### Pedidos

Os requisitos RF14 a RF18 são atendidos pelo `PedidoController`, que disponibiliza operações para:

* Criar pedidos;
* Listar pedidos;
* Buscar pedido por ID;
* Alterar o status do pedido;
* Cancelar pedidos.

Endpoint base:

```text
/api/v1/pedidos
```

---

## Implementação dos Requisitos Não Funcionais

### RNF01 — Java 17

O projeto utiliza Java 17 como versão da linguagem.

### RNF02 — Spring Boot

A aplicação foi desenvolvida utilizando Spring Boot.

### RNF03 — Banco H2

O projeto utiliza o banco de dados H2 em memória durante o desenvolvimento.

Configuração:

```text
jdbc:h2:mem:ecommerce
```

### RNF04 — Arquitetura MVC

A aplicação está organizada em camadas, separando responsabilidades entre controllers, services, repositories, entities e DTOs.

### RNF05 — API REST

Os recursos da aplicação são disponibilizados por meio de endpoints REST utilizando os métodos HTTP correspondentes às operações realizadas.

### RNF06 — JSON

A API utiliza JSON para troca de dados entre o cliente e o servidor.

### RNF07 — Bean Validation

A aplicação utiliza Bean Validation para validação dos dados recebidos nas requisições.

Também existe uma validação específica para SKU.

### RNF08 — Tratamento global de exceções

O projeto possui tratamento centralizado de exceções por meio do `GlobalExceptionHandler`.

Também são utilizadas exceções específicas, como:

* `BusinessException`;
* `ResourceNotFoundException`.

### RNF09 — Swagger/OpenAPI

A API possui documentação utilizando Swagger/OpenAPI.

Após iniciar a aplicação, a documentação pode ser acessada em:

```text
http://localhost:8080/swagger-ui.html
```

### RNF10 — Testes com JUnit e Mockito

O projeto possui testes automatizados utilizando JUnit e Mockito.

Os testes estão organizados principalmente nas camadas de Controller e Service.

---

## Resumo

Os requisitos funcionais RF01 a RF18 estão relacionados às funcionalidades de categorias, produtos, clientes e pedidos.

Os requisitos não funcionais RNF01 a RNF10 estão relacionados às tecnologias, arquitetura, persistência, comunicação, validação, tratamento de exceções, documentação e testes utilizados no projeto.
