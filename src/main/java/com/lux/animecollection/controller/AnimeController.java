package com.lux.animecollection.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return animeService.getAllAnimes();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Anime> getAnimeById(@PathVariable Long id) {
        return animeService.getAnimeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Anime createAnime(@RequestBody Anime anime) {
        return animeService.createAnime(anime);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Anime> updateAnime(@PathVariable Long id, @RequestBody Anime animeDetails) {
        return animeService.updateAnime(id, animeDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnime(@PathVariable Long id) {
        return animeService.deleteAnime(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
} 