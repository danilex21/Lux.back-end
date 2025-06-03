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
    
    public List<Anime> findAll() {
        logger.info("Buscando todos os animes");
        return animeRepository.findAll();
    }
    
    public Optional<Anime> findById(Long id) {
        logger.info("Buscando anime com ID: {}", id);
        return animeRepository.findById(id);
    }
    
    public List<Anime> findFavorites() {
        logger.info("Buscando animes favoritos");
        return animeRepository.findByIsFavoriteTrue();
    }
    
    public List<Anime> search(String query) {
        logger.info("Buscando animes com query: {}", query);
        return animeRepository.findByTitleContainingIgnoreCase(query);
    }
    
    public List<Anime> findByGenre(String genre) {
        logger.info("Buscando animes do gênero: {}", genre);
        return animeRepository.findByGenreIgnoreCase(genre);
    }
    
    @Transactional
    public Anime save(Anime anime) {
        logger.info("Salvando novo anime: {}", anime);
        try {
            validateAnime(anime);
            return animeRepository.save(anime);
        } catch (Exception e) {
            logger.error("Erro ao salvar anime: {}", e.getMessage());
            throw new RuntimeException("Erro ao salvar anime: " + e.getMessage());
        }
    }
    
    @Transactional
    public Anime update(Long id, Anime animeDetails) {
        logger.info("Atualizando anime com ID: {}", id);
        return animeRepository.findById(id).map(existingAnime -> {
            try {
                validateAnime(animeDetails);
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
                if (animeDetails.getImageUrl() != null) {
                    existingAnime.setImageUrl(animeDetails.getImageUrl());
                }
                return animeRepository.save(existingAnime);
            } catch (Exception e) {
                logger.error("Erro ao atualizar anime: {}", e.getMessage());
                throw new RuntimeException("Erro ao atualizar anime: " + e.getMessage());
            }
        }).orElseThrow(() -> new RuntimeException("Anime não encontrado"));
    }
    
    @Transactional
    public Anime toggleFavorite(Long id) {
        logger.info("Alternando status de favorito para anime com ID: {}", id);
        Anime anime = findById(id).orElseThrow(() -> new RuntimeException("Anime não encontrado"));
        anime.setFavorite(!anime.isFavorite());
        return animeRepository.save(anime);
    }
    
    @Transactional
    public void delete(Long id) {
        logger.info("Deletando anime com ID: {}", id);
        animeRepository.deleteById(id);
    }

    private void validateAnime(Anime anime) {
        if (anime.getTitle() == null || anime.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Título do anime é obrigatório");
        }
        if (anime.getRating() != null && (anime.getRating() < 0 || anime.getRating() > 10)) {
            throw new IllegalArgumentException("Avaliação deve estar entre 0 e 10");
        }
    }
}