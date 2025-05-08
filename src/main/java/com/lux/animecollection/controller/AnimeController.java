package com.lux.animecollection.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.lux.animecollection.dto.AnimeDTO;
import com.lux.animecollection.dto.AnimeResponseDTO;
import com.lux.animecollection.model.Anime;
import com.lux.animecollection.service.AnimeService;

@RestController
@RequestMapping("/api/animes")
@CrossOrigin(origins = "*")
public class AnimeController {
    
    @Autowired
    private AnimeService animeService;
    
    @GetMapping
    public List<AnimeResponseDTO> getAllAnimes() {
        return animeService.getAllAnimes().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AnimeResponseDTO> getAnimeById(@PathVariable Long id) {
        return animeService.getAnimeById(id)
                .map(this::convertToResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public AnimeResponseDTO createAnime(@RequestBody AnimeDTO animeDTO) {
        Anime anime = convertToEntity(animeDTO);
        Anime savedAnime = animeService.createAnime(anime);
        return convertToResponseDTO(savedAnime);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AnimeResponseDTO> updateAnime(@PathVariable Long id, @RequestBody AnimeDTO animeDTO) {
        Anime anime = convertToEntity(animeDTO);
        return animeService.updateAnime(id, anime)
                .map(this::convertToResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnime(@PathVariable Long id) {
        return animeService.deleteAnime(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    private Anime convertToEntity(AnimeDTO dto) {
        Anime anime = new Anime();
        anime.setTitle(dto.getTitle());
        anime.setDescription(dto.getDescription());
        anime.setImageUrl(dto.getImageUrl());
        anime.setStatus(dto.getStatus());
        anime.setEpisodes(dto.getEpisodes());
        anime.setRating(dto.getRating());
        anime.setGenre(dto.getGenre());
        anime.setType(dto.getType());
        anime.setReleaseDate(dto.getReleaseDate());
        return anime;
    }

    private AnimeResponseDTO convertToResponseDTO(Anime anime) {
        AnimeResponseDTO dto = new AnimeResponseDTO();
        dto.setId(anime.getId());
        dto.setTitle(anime.getTitle());
        dto.setDescription(anime.getDescription());
        dto.setImageUrl(anime.getImageUrl());
        dto.setStatus(anime.getStatus());
        dto.setEpisodes(anime.getEpisodes());
        dto.setRating(anime.getRating());
        dto.setGenre(anime.getGenre());
        dto.setType(anime.getType());
        dto.setReleaseDate(anime.getReleaseDate());
        return dto;
    }
} 