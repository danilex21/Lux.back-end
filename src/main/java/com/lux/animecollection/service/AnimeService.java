package com.lux.animecollection.service;

import com.lux.animecollection.model.Anime;
import com.lux.animecollection.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class AnimeService {
    
    private static final Logger logger = LoggerFactory.getLogger(AnimeService.class);
    
    @Autowired
    private AnimeRepository animeRepository;
    
    public List<Anime> getAllAnimes() {
        logger.info("Buscando todos os animes");
        return animeRepository.findAll();
    }
    
    public Optional<Anime> getAnimeById(Long id) {
        logger.info("Buscando anime com ID: {}", id);
        return animeRepository.findById(id);
    }
    
    public Anime createAnime(Anime anime) {
        try {
            logger.info("Tentando salvar anime: {}", anime);
            Anime savedAnime = animeRepository.save(anime);
            logger.info("Anime salvo com sucesso: {}", savedAnime);
            return savedAnime;
        } catch (Exception e) {
            logger.error("Erro ao salvar anime: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    public Optional<Anime> updateAnime(Long id, Anime animeDetails) {
        Optional<Anime> anime = animeRepository.findById(id);
        
        if (anime.isPresent()) {
            Anime existingAnime = anime.get();
            existingAnime.setTitle(animeDetails.getTitle());
            existingAnime.setDescription(animeDetails.getDescription());
            existingAnime.setImageUrl(animeDetails.getImageUrl());
            existingAnime.setRating(animeDetails.getRating());
            existingAnime.setGenre(animeDetails.getGenre());
            
            return Optional.of(animeRepository.save(existingAnime));
        }
        
        return Optional.empty();
    }
    
    public boolean deleteAnime(Long id) {
        Optional<Anime> anime = animeRepository.findById(id);
        
        if (anime.isPresent()) {
            animeRepository.delete(anime.get());
            return true;
        }
        
        return false;
    }
} 