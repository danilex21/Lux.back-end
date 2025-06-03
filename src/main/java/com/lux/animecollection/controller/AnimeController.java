package com.lux.animecollection.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lux.animecollection.model.Anime;
import com.lux.animecollection.service.AnimeService;

@RestController
@RequestMapping("/api/animes")
@CrossOrigin(origins = "*")
public class AnimeController {
    
    @Autowired
    private AnimeService animeService;

    @GetMapping
    public List<Anime> getAllAnimes() {
        return animeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anime> getAnimeById(@PathVariable Long id) {
        return animeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/favorites")
    public List<Anime> getFavoriteAnimes() {
        return animeService.findFavorites();
    }

    @GetMapping("/search")
    public List<Anime> searchAnimes(@RequestParam String query) {
        return animeService.searchByTitle(query);
    }

    @GetMapping("/genre/{genre}")
    public List<Anime> getAnimesByGenre(@PathVariable String genre) {
        return animeService.findByGenre(genre);
    }

    @PostMapping
    public ResponseEntity<?> createAnime(@RequestBody Anime anime) {
        try {
            if (anime == null) {
                return ResponseEntity.badRequest().body("Dados do anime não podem ser nulos");
            }

            if (anime.getTitle() == null || anime.getTitle().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Título do anime é obrigatório");
            }

            if (anime.getRating() != null && (anime.getRating() < 0 || anime.getRating() > 10)) {
                return ResponseEntity.badRequest().body("Avaliação deve estar entre 0 e 10");
            }

            Anime savedAnime = animeService.save(anime);
            return ResponseEntity.ok(savedAnime);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao criar anime: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAnime(@PathVariable Long id, @RequestBody Anime anime) {
        try {
            return animeService.findById(id)
                    .map(existingAnime -> {
                        anime.setId(id);
                        return ResponseEntity.ok(animeService.save(anime));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao atualizar anime: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/favorite")
    public ResponseEntity<?> toggleFavorite(@PathVariable Long id) {
        try {
            return animeService.findById(id)
                    .map(anime -> {
                        anime.setFavorite(!anime.isFavorite());
                        return ResponseEntity.ok(animeService.save(anime));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao alternar favorito: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnime(@PathVariable Long id) {
        try {
            return animeService.findById(id)
                    .map(anime -> {
                        animeService.delete(id);
                        return ResponseEntity.ok().build();
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao deletar anime: " + e.getMessage());
        }
    }
} 