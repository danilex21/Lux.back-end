package com.lux.animecollection.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lux.animecollection.model.Anime;
import com.lux.animecollection.repository.AnimeRepository;

@Service
public class AnimeService {

    @Autowired
    private AnimeRepository animeRepository;

    public List<Anime> findAll() {
        return animeRepository.findAll();
    }

    public Optional<Anime> findById(Long id) {
        return animeRepository.findById(id);
    }

    public List<Anime> findFavorites() {
        return animeRepository.findByIsFavoriteTrue();
    }

    public List<Anime> searchByTitle(String query) {
        return animeRepository.findByTitleContainingIgnoreCase(query);
    }

    public List<Anime> findByGenre(String genre) {
        return animeRepository.findByGenreIgnoreCase(genre);    
    }

    public Anime save(Anime anime) {
        if (anime == null) {
            throw new IllegalArgumentException("Dados do anime não podem ser nulos");
        }
        if (anime.getTitle() == null || anime.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Título do anime é obrigatório");
        }
        return animeRepository.save(anime);
    }

    public void delete(Long id) {
        animeRepository.deleteById(id);
    }
} 