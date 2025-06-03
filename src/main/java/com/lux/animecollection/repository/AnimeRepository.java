package com.lux.animecollection.repository;

import com.lux.animecollection.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {
    List<Anime> findByIsFavoriteTrue();
    List<Anime> findByTitleContainingIgnoreCase(String title);
    List<Anime> findByGenreIgnoreCase(String genre);
} 