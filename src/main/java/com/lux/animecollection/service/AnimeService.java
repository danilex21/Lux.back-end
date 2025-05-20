package com.lux.animecollection.service;

import com.lux.animecollection.model.Anime;
import com.lux.animecollection.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimeService {
    
    @Autowired
    private AnimeRepository animeRepository;
    
    public List<Anime> getAllAnimes() {
        return animeRepository.findAll();
    }
    
    public Optional<Anime> getAnimeById(Long id) {
        return animeRepository.findById(id);
    }
    
    public Anime createAnime(Anime anime) {
        return animeRepository.save(anime);
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