package com.lux.animecollection.controller;

import com.lux.animecollection.model.Anime;
import com.lux.animecollection.service.AnimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/animes")
@CrossOrigin(origins = "*")
public class AnimeController {
    
    private static final Logger logger = LoggerFactory.getLogger(AnimeController.class);
    
    private final AnimeService animeService;
    
    @Autowired
    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @GetMapping
    public ResponseEntity<List<Anime>> getAllAnimes() {
        logger.info("Recebendo requisição para listar todos os animes");
        return ResponseEntity.ok(animeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anime> getAnime(@PathVariable Long id) {
        logger.info("Recebendo requisição para buscar anime com ID: {}", id);
        return animeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<Anime>> getFavoriteAnimes() {
        logger.info("Recebendo requisição para listar animes favoritos");
        return ResponseEntity.ok(animeService.findFavorites());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Anime>> searchAnimes(@RequestParam String query) {
        logger.info("Recebendo requisição para buscar animes com query: {}", query);
        return ResponseEntity.ok(animeService.search(query));
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Anime>> getAnimesByGenre(@PathVariable String genre) {
        logger.info("Recebendo requisição para listar animes do gênero: {}", genre);
        return ResponseEntity.ok(animeService.findByGenre(genre));
    }

    @PostMapping
    public ResponseEntity<Anime> createAnime(@RequestBody Anime anime) {
        logger.info("Recebendo requisição para criar novo anime: {}", anime);
        try {
            if (anime.getTitle() == null || anime.getTitle().trim().isEmpty()) {
                logger.error("Título do anime é obrigatório");
                return ResponseEntity.badRequest().build();
            }
            Anime savedAnime = animeService.save(anime);
            logger.info("Anime criado com sucesso: {}", savedAnime);
            return ResponseEntity.ok(savedAnime);
        } catch (Exception e) {
            logger.error("Erro ao criar anime: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Anime> updateAnime(@PathVariable Long id, @RequestBody Anime anime) {
        logger.info("Recebendo requisição para atualizar anime com ID: {}", id);
        try {
            if (anime.getTitle() == null || anime.getTitle().trim().isEmpty()) {
                logger.error("Título do anime é obrigatório");
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(animeService.update(id, anime));
        } catch (RuntimeException e) {
            logger.error("Erro ao atualizar anime: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Erro ao atualizar anime: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/favorite")
    public ResponseEntity<Anime> toggleFavorite(@PathVariable Long id) {
        logger.info("Recebendo requisição para alternar favorito do anime com ID: {}", id);
        try {
            return ResponseEntity.ok(animeService.toggleFavorite(id));
        } catch (RuntimeException e) {
            logger.error("Erro ao alternar favorito: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnime(@PathVariable Long id) {
        logger.info("Recebendo requisição para deletar anime com ID: {}", id);
        try {
            animeService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Erro ao deletar anime: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}