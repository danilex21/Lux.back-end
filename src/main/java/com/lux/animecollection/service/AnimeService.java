package com.lux.animecollection.service;

import com.lux.animecollection.model.Anime;
import com.lux.animecollection.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

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
    
    @Transactional
    public Anime updateAnime(Long id, Anime animeDetails) {
        return animeRepository.findById(id).map(existingAnime -> {
            // Update only the fields that are not null in animeDetails
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
            // Update image data if provided
            if (animeDetails.getImageData() != null) {
                existingAnime.setImageData(animeDetails.getImageData());
                existingAnime.setImageType(animeDetails.getImageType());
            }
            
            return animeRepository.save(existingAnime);
        }).orElse(null);
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