package com.lux.animecollection.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lux.animecollection.model.Anime;
import com.lux.animecollection.repository.AnimeRepository;

@Service
public class AnimeService {

    @Autowired
    private AnimeRepository animeRepository;

    public List<Anime> findAll() {
        try {
            return animeRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar animes: " + e.getMessage());
        }
    }

    public Optional<Anime> findById(Long id) {
        try {
            return animeRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar anime por ID: " + e.getMessage());
        }
    }

    public List<Anime> findFavorites() {
        try {
            return animeRepository.findByIsFavoriteTrue();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar favoritos: " + e.getMessage());
        }
    }

    public List<Anime> searchByTitle(String query) {
        try {
            return animeRepository.findByTitleContainingIgnoreCase(query);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar por título: " + e.getMessage());
        }
    }

    public List<Anime> findByGenre(String genre) {
        try {
            return animeRepository.findByGenreIgnoreCase(genre);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar por gênero: " + e.getMessage());
        }
    }

    @Transactional
    public Anime save(Anime anime) {
        try {
            if (anime == null) {
                throw new IllegalArgumentException("Dados do anime não podem ser nulos");
            }
            if (anime.getTitle() == null || anime.getTitle().trim().isEmpty()) {
                throw new IllegalArgumentException("Título do anime é obrigatório");
            }
            if (anime.getRating() != null && (anime.getRating() < 0 || anime.getRating() > 10)) {
                throw new IllegalArgumentException("Avaliação deve estar entre 0 e 10");
            }
            return animeRepository.save(anime);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar anime: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            animeRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar anime: " + e.getMessage());
        }
    }
} 