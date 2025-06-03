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
    public Anime createAnime(@RequestBody Anime anime) {
        return animeService.save(anime);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Anime> updateAnime(@PathVariable Long id, @RequestBody Anime anime) {
        return animeService.findById(id)
                .map(existingAnime -> {
                    anime.setId(id);
                    return ResponseEntity.ok(animeService.save(anime));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/favorite")
    public ResponseEntity<Anime> toggleFavorite(@PathVariable Long id) {
        return animeService.findById(id)
                .map(anime -> {
                    anime.setFavorite(!anime.isFavorite());
                    return ResponseEntity.ok(animeService.save(anime));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnime(@PathVariable Long id) {
        return animeService.findById(id)
                .map(anime -> {
                    animeService.delete(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 