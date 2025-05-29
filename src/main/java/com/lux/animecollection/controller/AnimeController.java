package com.lux.animecollection.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.lux.animecollection.dto.AnimeDTO;
import com.lux.animecollection.model.Anime;
import com.lux.animecollection.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador responsável por gerenciar as operações de API relacionadas aos animes.
 * Fornece endpoints para listar, buscar, criar, atualizar e excluir animes.
 */
@RestController
@RequestMapping("/api/animes")
@CrossOrigin("*")
public class AnimeController {

    /**
     * Serviço que contém a lógica de negócios para operações com animes.
     */
    @Autowired
    private AnimeService animeService;
    
    /**
     * Retorna uma lista de todos os animes disponíveis.
     * Converte cada entidade Anime para AnimeDTO antes de retornar.
     * 
     * @return Lista de DTOs de animes
     */
    @GetMapping
    public List<AnimeDTO> getAllAnimes() {
        return animeService.getAllAnimes().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }   

    /**
     * Busca um anime específico pelo seu ID.
     * 
     * @param id O identificador único do anime
     * @return O DTO do anime encontrado ou vazio se não existir
     */
    @GetMapping("/{id}")
    public Optional<AnimeDTO> getAnimeById(@PathVariable Long id) {
        return animeService.getAnimeById(id).map(this::convertToDTO);
    }
    
    /**
     * Cria um novo anime no sistema.
     * 
     * @param anime O objeto anime a ser criado
     * @return O DTO do anime criado
     */
    @PostMapping
    public AnimeDTO createAnime(@RequestBody Anime anime) {
        return convertToDTO(animeService.createAnime(anime));
    }

    /**
     * Atualiza um anime existente com novas informações.
     * 
     * @param id O identificador único do anime a ser atualizado
     * @param animeDetails As novas informações do anime
     * @return O DTO do anime atualizado
     */
    @PutMapping("/{id}")
    public AnimeDTO updateAnime(@PathVariable Long id, @RequestBody Anime animeDetails) {
        return convertToDTO(animeService.updateAnime(id, animeDetails));
    }

    /**
     * Remove um anime do sistema.
     * 
     * @param id O identificador único do anime a ser removido
     * @return true se o anime foi removido com sucesso, false caso contrário
     */
    @DeleteMapping("/{id}")
    public boolean deleteAnime(@PathVariable Long id) {
        return animeService.deleteAnime(id);
    }

    /**
     * Método auxiliar para converter uma entidade Anime em um DTO.
     * 
     * @param anime A entidade Anime a ser convertida
     * @return O DTO correspondente à entidade
     */
    private AnimeDTO convertToDTO(Anime anime) {
        AnimeDTO dto = new AnimeDTO();
        dto.setId(anime.getId());
        dto.setTitle(anime.getTitle());
        dto.setDescription(anime.getDescription());
        dto.setRating(anime.getRating());
        dto.setGenre(anime.getGenre());
        return dto;
    }
}
