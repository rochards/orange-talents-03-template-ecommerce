# Desafio Mercado Livre

### Entidades do sistema
- Usuario;
- Categoria;
- Produto;
- CaracteristicaProduto;
- ImagemProduto;
- Opiniao;
- Pergunta;

### Funcionalidades do sistema
- Cadastro de usuário;
- Cadastro de categoria;
- Autenticação de usuário;
- Cadastro de produto e suas características;
- Cadastro de imagens do produto;
- Adição de opinião ao produto;
- Adição de pergunta sobre o produto;

### Operações
- POST /usuarios;
- POST /categorias;
- POST /auth;
- POST /produtos;
- POST /produtos/{id}/imagens
- POST /produtos/{id}/opinioes
- POST /produtos/{id}/perguntas