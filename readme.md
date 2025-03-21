# 🛒 E-commerce API

API RESTful para gerenciamento de um sistema de e-commerce, incluindo usuários, produtos, pedidos e relatórios administrativos. Desenvolvido com **Java 17**, **Spring Boot 3** e **Spring Data JPA**.

---

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3**
- **Spring Security com JWT**
- **Spring Data JPA**
- **Banco MySQL**
- **Maven**

---

## 📁 Estrutura de Pacotes

```bash
com.ecommerce
├── application      # DTOs e serviços de aplicação
├── domain           # Entidades, repositórios e enums
├── infrastructure   # Controllers, configurações gerais e exceções
```
# ✅ Requisitos

- Java 17+
- Maven
- Git
- Mysql Server 8+
- Postman

# 🚀 Como Executar

### 1. Pré-requisitos

Certifique-se de ter instalado:

- [Java 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/install.html)
- [Git](https://git-scm.com/)
- [MySQL Server 8+](https://dev.mysql.com/downloads/mysql/)
- [MySQL Workbench (opcional, para gerenciamento visual)](https://dev.mysql.com/downloads/workbench/)
- [Postman](https://www.postman.com/downloads/)

### 2. Clonar o repositório

- Clone o repositório do github, colocando em um caminho de sua preferência.

### 3. Executar os scripts SQL no MySQL

- Agora dentro da pasta `/doc/BD`, execute os arquivos SQL abaixo em seu banco de dados:


- schemas.sql: cria o banco de dados e as tabelas necessárias.
- seeds.sql: popula o banco com dados iniciais.
- procedure.sql (opcional): cria uma procedure para gerar pedidos de teste com diferentes status.
- procedure-execution: Roda a procedure, criando 1000 pedidos.


### 4. Configurar o application.properties

- Abra o arquivo na pasta `/src/main/resources/application.properties` e atualize os seguintes campos com os dados do seu banco:
```code
spring.application.name=ecommerce-api
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### 5. Compilar e instalar as dependências
- Agora, o Maven se encarregará de baixar todas as dependências necessárias com:
```bash 
  mvn clean install 
```

- Esse comando irá compilar o projeto e baixar as dependências declaradas no pom.xml

### 6. Rodar a aplicação
- Para rodar a aplicação localmente, execute:
``` bash
    mvn spring-boot:run
```

### 7. Acessar endpoints via Postman

- Baixe o arquivo de coleção Postman `Ecommerce - Case.postman_collection.json`, disponível na pasta `/doc` do projeto.
- Abra o [Postman](https://www.postman.com/downloads/).
- Clique em **Import**, selecione a aba **File**, e carregue o arquivo da coleção.
- Após importar, você verá todas as requisições organizadas por grupos (Login e Cadastro, Produto, Pedido, Relatório).
- Lembre-se de autenticar com um usuário existente, disponível no arquivo seeds, na pasta `/docs/BD`, ou cadastrar um novo no banco de dados para gerar um token JWT.
- O token deve ser incluído no header de cada requisição protegida:  
  `Authorization: Bearer <seu_token_aqui>`

> 💡 Algumas requisições da coleção já incluem um token expirado apenas como exemplo. Substitua pelo seu token válido após autenticação.

# 🔐 Autenticação

A aplicação utiliza **JWT** para autenticação, seu uso foi explicado acima.

## 📦 Principais Endpoints
- TODOS os endpoints menos o de login exigem no mínimo o token JWT, explicada acima como usar.

### 👤 Usuários - `/usuarios`
- `POST /cadastrarAdmin`: Cadastra um novo usuário com perfil ADMIN (requer token)
- `POST /cadastrarUser`: Cadastra um novo usuário com perfil USER (requer token)

### 🔐 Autenticação - `/auth`
- `POST /login`: Autentica o usuário e retorna um token JWT

### 📦 Produtos - `/produtos`
- `GET /`: Lista todos os produtos (requer token)
- `POST /`: Cadastra um novo produto (ADMIN)
- `PUT /atualizar`: Atualiza os dados de um produto (ADMIN)
- `DELETE /deletar`: Remove um produto pelo ID (ADMIN)
- `POST /buscar`: Retorna os detalhes de um produto pelo ID

### 🧾 Pedidos - `/pedidos`
- `POST /`: Cria um novo pedido (requer token)
- `POST /pagar`: Paga um pedido pendente (requer token)
- `GET /`: Lista os pedidos do usuário logado
- `GET /todos`: Lista todos os pedidos (ADMIN)

### 📊 Relatórios - `/relatorios`
- `GET /top-compradores`: Exibe o Top 5 usuários com mais pedidos pagos
- `GET /ticket-medio`: Mostra a média de valor de pedidos por usuário
- `POST /faturamento`: Retorna o total de faturamento por período

---

## 💡 Notas

- Os pedidos possuem status: `PENDENTE`, `PAGO` ou `CANCELADO`.
- O valor total do pedido é calculado dinamicamente com base nos preços dos produtos no momento do pagamento.
- O sistema atualiza o estoque dos produtos no momento do pagamento.
- O tratamento de exceções é feito globalmente via `@ControllerAdvice`.
