# Lux Anime Collection - Backend

Backend para o aplicativo Lux Anime Collection, desenvolvido com Spring Boot.

## Tecnologias

- Java 21
- Spring Boot 3.2.1
- MySQL
- Maven

## Configuração

1. Clone o repositório
2. Configure o banco de dados no `application.properties`
3. Execute `mvn spring-boot:run`

## Endpoints

### Animes
- `GET /api/animes` - Lista todos os animes
- `GET /api/animes/{id}` - Busca anime por ID
- `GET /api/animes/favorites` - Lista animes favoritos
- `GET /api/animes/search?query={query}` - Busca animes por título
- `GET /api/animes/genre/{genre}` - Lista animes por gênero
- `POST /api/animes` - Cria novo anime
- `PUT /api/animes/{id}` - Atualiza anime
- `PUT /api/animes/{id}/favorite` - Alterna favorito
- `DELETE /api/animes/{id}` - Remove anime

## Modelo

```json
{
  "id": "Long",
  "title": "String",
  "description": "String",
  "rating": "Double",
  "genre": "String",
  "imageUrl": "String",
  "isFavorite": "Boolean"
}
```

## Desenvolvimento

- Frontend: [Lux.front-end-expo](https://github.com/danilex21/Lux.front-end-expo)
- Backend: Este repositório
