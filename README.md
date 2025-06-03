# 🎬 Lux Anime Collection - Backend

<div align="center">
  <img src="https://raw.githubusercontent.com/danilex21/Lux.front-end-expo/main/assets/icon.png" alt="Lux Anime Collection Logo" width="200"/>
  
  [![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.java.com)
  [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
  [![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com)
  [![Maven](https://img.shields.io/badge/Maven-3.9-red.svg)](https://maven.apache.org)
  [![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
</div>

## 📋 Sobre o Projeto

Backend do aplicativo Lux Anime Collection, desenvolvido com Spring Boot. Este projeto fornece uma API RESTful para gerenciar uma coleção de animes, permitindo operações CRUD, busca por título, gênero e gerenciamento de favoritos.

## 🚀 Tecnologias

- **Java 21**: Linguagem de programação principal
- **Spring Boot 3.2.1**: Framework para desenvolvimento web
- **MySQL**: Banco de dados relacional
- **Maven**: Gerenciador de dependências
- **JPA/Hibernate**: ORM para persistência de dados
- **REST**: Arquitetura da API

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── lux/
│   │           └── animecollection/
│   │               ├── controller/    # Controladores REST
│   │               ├── model/         # Entidades JPA
│   │               ├── repository/    # Repositórios de dados
│   │               ├── service/       # Lógica de negócios
│   │               └── AnimeCollectionApplication.java
│   └── resources/
│       └── application.properties     # Configurações
└── test/                            # Testes unitários
```

## 📡 Endpoints da API

### Animes
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/animes` | Lista todos os animes |
| GET | `/api/animes/{id}` | Busca anime por ID |
| GET | `/api/animes/favorites` | Lista animes favoritos |
| GET | `/api/animes/search?query={query}` | Busca animes por título |
| GET | `/api/animes/genre/{genre}` | Lista animes por gênero |
| POST | `/api/animes` | Cria novo anime |
| PUT | `/api/animes/{id}` | Atualiza anime |
| PUT | `/api/animes/{id}/favorite` | Alterna favorito |
| DELETE | `/api/animes/{id}` | Remove anime |

## 📦 Modelo de Dados

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

## 👥 Equipe

<div align="center">
  <table>
    <tr>
      <td align="center">
        <a href="https://github.com/xyms6">
          <img src="https://github.com/xyms6.png" width="100px;" alt="Luis"/>
          <br />
          <sub><b>Luis</b></sub>
        </a>
      </td>
      <td align="center">
        <a href="https://github.com/Leycon05">
          <img src="https://github.com/Leycon05.png" width="100px;" alt="Leycon"/>
          <br />
          <sub><b>Leycon</b></sub>
        </a>
      </td>
      <td align="center">
        <a href="https://github.com/AdryanSousa7">
          <img src="https://github.com/AdryanSousa7.png" width="100px;" alt="Adryan"/>
          <br />
          <sub><b>Adryan</b></sub>
        </a>
      </td>
    </tr>
  </table>
</div>

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 🔗 Links

- [Frontend](https://github.com/danilex21/Lux.front-end-expo)
- [Documentação da API](https://anime-collection-nf6r.onrender.com/swagger-ui.html)

---

