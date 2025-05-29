package com.lux.animecollection.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lux.animecollection.dto.AnimeDTO;
import com.lux.animecollection.model.Anime;
import com.lux.animecollection.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controlador responsável por gerenciar as operações de API relacionadas aos animes.
 * Fornece endpoints para listar, buscar, criar, atualizar e excluir animes.
 */
@RestController
@RequestMapping("/api/animes")
@CrossOrigin(
    origins = "*",
    allowedHeaders = {"Content-Type", "Authorization"},
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
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
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createAnime(
            @RequestPart("anime") String animeJson,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Anime anime = objectMapper.readValue(animeJson, Anime.class);
            
            if (anime.getTitle() == null || anime.getTitle().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("O título do anime é obrigatório");
            }
            
            if (imageFile != null && !imageFile.isEmpty()) {
                if (!imageFile.getContentType().startsWith("image/")) {
                    return ResponseEntity.badRequest().body("O arquivo deve ser uma imagem");
                }
                anime.setImageData(imageFile.getBytes());
                anime.setImageType(imageFile.getContentType());
            }
            
            Anime createdAnime = animeService.createAnime(anime);
            return ResponseEntity.ok(convertToDTO(createdAnime));
            
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Erro ao processar a requisição: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno ao processar a requisição");
        }
    }

         
    
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getAnimeImage(@PathVariable Long id) {
        return animeService.getAnimeById(id)
                .map(anime -> ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(anime.getImageType()))
                        .body(anime.getImageData()))
                .orElse(ResponseEntity.notFound().build());
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
        if (anime == null) return null;
        
        AnimeDTO dto = new AnimeDTO();
        dto.setId(anime.getId());
        dto.setTitle(anime.getTitle());
        dto.setDescription(anime.getDescription());
        dto.setRating(anime.getRating());
        dto.setGenre(anime.getGenre());
        
        // Set image URL if image data exists
        if (anime.getImageData() != null && anime.getImageType() != null) {
            dto.setImageUrl(anime.getImageUrl());
        }
        
        return dto;
    }
}
