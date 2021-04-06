# Desafio Mercado Livre

### Entidades do sistema
- Usuario;
- Categoria;
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
