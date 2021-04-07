# Desafio Mercado Livre

Esse desafio faz parte do programa de formação [Orange Talents](https://www.zup.com.br/orange-talents/) da [Zup](https://www.zup.com.br/).

### O que foi preciso para desenvolver esse projeto
- Java 11;
- Spring Framework:
  - [Spring Boot 2.4.4](https://spring.io/projects/spring-boot);
  - [Spring Security 5.4.5](https://spring.io/projects/spring-security);
- MySQL 8.
- [Java JWT](https://github.com/jwtk/jjwt);

## Entidades do sistema
### Usuário
#### Informações a serem cadastradas
- login;
- senha;
#### Restrições do cadastro
- O login não poder ser em branco e precisa ser um e-mail;
- O login precisa ser único no sistema;
- O formato do e-mail precisa ser validado;
- A senha não pode ser em branco e precisa ter no mínimo 6 caracteres;
- O instante de cadastro do usuário precisa ser registrado;
#### Cadastro
- POST http://localhost:8080/usuarios
- Corpo da requisicao:
  ```yaml
  {
    "login": "exemplo@email.com.br",
    "senha": "exemplo123"
  }
  ```
#### Respostas do API
- Status 200 como resposta de sucesso e JSON;
  - Ex.:
    ```yaml
    {
      "id": 1,
      "login": "exemplo@email.com.br",
      "criadoEm": "2021-04-07T10:34:13.7615381-03:00"
    }
    ```
- Status 400 em falha de validação e JSON:
  - Ex.:
    ```yaml
    {
      "timestamp": "2021-04-07T10:38:45.4393226-03:00",
      "status": 400,
      "errors": [
        {
            "field": "login",
            "message": "não deve estar em branco"
        }
      ]
    }
    ```
### Categoria
No mercado livre você pode criar hierarquias de categorias livres. Ex: Tecnologia -> Celulares -> Smartphones ->
#### Informações a serem cadastradas
- nome;
- possível categoria mãe (não é obrigatório).
#### Restrições do cadastro
- o nome da categoria é obrigatório e precisa ser único.
#### Cadastro
- POST http://localhost:8080/categorias
- Corpo da requisicao:
  ```yaml
  {
    "nome": "Celulares",
    "categoriaMaeId": 1
  }
  ```
#### Respostas do API
- Status 200 como resposta de sucesso;
- Status 400 em falha de validação e JSON:
  - Ex.:
    ```yaml
    {
      "timestamp": "2021-04-07T10:38:45.4393226-03:00",
      "status": 400,
      "errors": [
        {
            "field": "nome",
            "message": "esse nome já está cadastrado"
        }
      ]
    }
    ```
### Autenticação de usuário
Nessa etapa configura-se um mecanismo de autenticação via token com o Spring Security e a biblioteca JJWT.  
Na classe de configuração do Spring Security apenas os endpoints /auth e /usuarios estão liberados para acesso sem autenticação. Os demais exigem que um usuário esteja autenticado.
#### Autenticação
- POST http://localhost:8080/auth
- Corpo da requisicao:
  ```yaml
  {
    "login": "exemplo@email.com.br",
    "senha": "123456"
  }
  ```
#### Respostas do API
- Status 200 como resposta de sucesso e JSON:
  - Ex.:
    ```yaml
    {
      "tipo": "Bearer",
      "toke": ""eyJhbGciOiJIUzUxMiJ9. ... -NKfwLYgQ"
    }
    ```
- Status 400 em falha de validação e JSON:
  - Ex.:
    ```yaml
    {
      "timestamp": "2021-04-07T10:38:45.4393226-03:00",
      "status": 400,
      "errors": [
        {
             "field": "login",
             "message": "não há usuário cadastrado com o e-mail informado"
        }
      ]
    }
    ```
### Entidades do sistema
- Produto;
- CaracteristicaProduto;
- ImagemProduto;
- Opiniao;
- Pergunta;
- Compra;
- Pagamento;

### Funcionalidades do sistema
- Cadastro de usuário;
- Cadastro de categoria;
- Autenticação de usuário;
- Cadastro de produto e suas características;
- Cadastro de imagens do produto;
- Adição de opinião ao produto;
- Adição de pergunta sobre o produto;
- Consulta de produtos;
- Realização de compra;
- Processa pagamentos;
- Integração com sistema de notas fiscais;
- Integração com sistema de ranking de vendedores;

### Operações
- POST /usuarios;
- POST /categorias;
- POST /auth;
- POST /produtos;
- POST /produtos/{id}/imagens;
- POST /produtos/{id}/opinioes;
- POST /produtos/{id}/perguntas;
- GET /produtos/{id};
- POST /compras;
- POST /pagamentos;
- POST /notas_fiscais;
- POST /ranking_vendedores;
