## Projeto Biblioteca MVC desenvolvido em Java - Spring MVC/Spring Boot

 **Observação para login inicial** :
  - username: admin / password: 123456 (Administrador)
 - username: usuario / password: 123456 (Usuário da Biblioteca)

### Funcionalidades:

Administradores:
- Cadastro de Autores
- Cadastro de Livros com upload da capa
- Cadastro de Usuários

Usuários da biblioteca:
- Empréstimos de livros através de painel apresentando a capa cadastrada de cada livro
- Visualização da média das avaliações de cada livro em forma de rating (estrelas)
- Visualização detalhada de cada review cadastrado ao clicar na capa do livro
- Área "Meus Empréstimos": visualização de todos os empréstimos realizados pelo usuário e gerenciamento de devoluções
- Área "Meus Empréstimos": possibilidade de registrar devolução cadastrando também um review do livro devolvido e avaliação em forma de rating.


 


### Recursos técnicos:

- Java 8
- Spring Boot/Spring MVC/Spring Data JPA
- Template Bootstrap
- Spring Security (autenticação e autorização)
- Testes automatizados dos controllers, services e testes funcionais utilizando Selenium. 