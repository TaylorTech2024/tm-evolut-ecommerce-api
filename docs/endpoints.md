# Endpoints da API

A aplicação disponibiliza uma API REST para gerenciamento de categorias, produtos, clientes e pedidos.

## Categorias

Base:

```text
/api/v1/categorias
```

Operações disponíveis:

```text
GET    /api/v1/categorias
GET    /api/v1/categorias/{id}
POST   /api/v1/categorias
PUT    /api/v1/categorias/{id}
DELETE /api/v1/categorias/{id}
```

## Produtos

Base:

```text
/api/v1/produtos
```

Operações disponíveis:

```text
GET    /api/v1/produtos
GET    /api/v1/produtos/{id}
POST   /api/v1/produtos
PUT    /api/v1/produtos/{id}
PATCH  /api/v1/produtos/{id}
DELETE /api/v1/produtos/{id}
```

A consulta de produtos também possui recursos de filtro e paginação.

## Clientes

A API possui endpoints para gerenciamento de clientes através do `ClienteController`.

As operações disponibilizadas pelo controller devem ser utilizadas conforme a documentação gerada pelo Swagger/OpenAPI.

## Pedidos

A API possui endpoints para gerenciamento de pedidos através do `PedidoController`.

Os pedidos também possuem relacionamento com os itens de pedido e utilização do status do pedido.

## Documentação completa

A documentação detalhada dos endpoints pode ser consultada através do Swagger/OpenAPI:

```text
http://localhost:8080/swagger-ui.html
```

Especificação OpenAPI:

```text
http://localhost:8080/v3/api-docs
```
