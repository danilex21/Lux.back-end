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
        logger.info("Atualizando anime com ID: {}", id);
        return animeRepository.findById(id).map(existingAnime -> {
            if (animeDetails.getTitle() != null) {
                existingAnime.setTitle(animeDetails.getTitle());
            }
            if (animeDetails.getDescription() != null) {
                existingAnime.setDescription(animeDetails.getDescription());
            }
            if (animeDetails.getRating() != null) {
                existingAnime.setRating(animeDetails.getRating());
            }
            if (animeDetails.getGenre() != null) {
                existingAnime.setGenre(animeDetails.getGenre());
            }
            if (animeDetails.getImageData() != null) {
                existingAnime.setImageData(animeDetails.getImageData());
                existingAnime.setImageType(animeDetails.getImageType());
            }
            return animeRepository.save(existingAnime);
        }).orElse(null);
    }
    
    @Transactional
    public boolean deleteAnime(Long id) {
        logger.info("Deletando anime com ID: {}", id);
        try {
            animeRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            logger.error("Erro ao deletar anime com ID {}: {}", id, e.getMessage());
            return false;
        }
    }
}