package com.lux.animecollection.service;

import com.lux.animecollection.model.Anime;
import com.lux.animecollection.repository.AnimeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AnimeService {
    
    private static final Logger logger = LoggerFactory.getLogger(AnimeService.class);
    
    private final AnimeRepository animeRepository;
    
    @Autowired
    public AnimeService(AnimeRepository animeRepository) {
        this.animeRepository = animeRepository;
    }
    
    public List<Anime> getAllAnimes() {
        logger.info("Buscando todos os animes");
        return animeRepository.findAll();
    }
    
    public Optional<Anime> getAnimeById(Long id) {
        logger.info("Buscando anime com ID: {}", id);
        return animeRepository.findById(id);
    }
    
    @Transactional
    public Anime createAnime(Anime anime) {
        logger.info("Criando novo anime: {}", anime.getTitle());
        return animeRepository.save(anime);
    }
    
    @Transactional
    public Anime updateAnime(Long id, Anime animeDetails) {
        return animeRepository.findById(id).map(existingAnime -> {
            existingAnime.setTitle(animeDetails.getTitle());
            existingAnime.setDescription(animeDetails.getDescription());
            existingAnime.setRating(animeDetails.getRating());
            existingAnime.setGenre(animeDetails.getGenre());
            return animeRepository.save(existingAnime);
        }).orElse(null);
    }
    
    @Transactional
    public boolean deleteAnime(Long id) {
        try {
            animeRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}