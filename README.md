# Anime Collection Backend 🎬

Este é o backend do aplicativo Anime Collection, desenvolvido com Spring Boot 3.2.1 e Java 21. 🚀

## Requisitos 📋

- Java 21 ☕
- MySQL 8.0+ 🛢️
- Maven 🔧

## Configuração ⚙️

1. Clone o repositório 📥
2. Configure o banco de dados MySQL no arquivo `src/main/resources/application.properties` 📝
3. Execute o comando: `mvn spring-boot:run` 🏃

## Endpoints da API 🌐

### Animes 📺

- `GET /api/animes` - Lista todos os animes 📋
- `GET /api/animes/{id}` - Obtém um anime específico 🔍
- `POST /api/animes` - Cria um novo anime ➕
- `PUT /api/animes/{id}` - Atualiza um anime existente 🔄
- `DELETE /api/animes/{id}` - Remove um anime ❌

## Modelo de Dados 💾

### Anime 🎭

```json
{
  "id": 1,
  "title": "Nome do Anime",
  "description": "Descrição do Anime",
  "imageUrl": "URL da Imagem",
  "rating": 4.5,
  "genre": "Ação"
}
```

## Desenvolvimento 👨‍💻

O projeto usa as seguintes tecnologias:

- Spring Boot 🍃
- Spring Data JPA 📊
- MySQL 🛢️
- Spring Boot DevTools 🛠️
- SpringDoc OpenAPI (Swagger) 📚

## Documentação da API 📖

A documentação da API está disponível através do Swagger UI. Após iniciar a aplicação, acesse:

```
http://localhost:8080/swagger-ui/index.html
```

## Configuração do Banco de Dados 🗄️

O banco de dados será criado automaticamente ao iniciar a aplicação pela primeira vez. Certifique-se de que o MySQL está em execução e que as credenciais no arquivo `application.properties` estão corretas. ✅
