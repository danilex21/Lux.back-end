package com.lux.animecollection.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lux.animecollection.dto.AnimeDTO;
import com.lux.animecollection.model.Anime;
import com.lux.animecollection.service.AnimeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/animes")
@CrossOrigin(origins = "*")
@Tag(name = "Anime", description = "API para operações de gerenciamento de animes")
public class AnimeController {
    
    @Autowired
    private AnimeService animeService;
    
    @Operation(summary = "Listar todos os animes", description = "Retorna uma lista com todos os animes cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de animes recuperada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnimeDTO.class)))
    })
    @GetMapping
    public List<AnimeDTO> getAllAnimes() {
        return animeService.getAllAnimes().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Operation(summary = "Obter anime por ID", description = "Retorna um único anime com base no ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Anime encontrado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnimeDTO.class))),
        @ApiResponse(responseCode = "404", description = "Anime não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<AnimeDTO> getAnimeById(
            @Parameter(description = "ID do anime a ser obtido", required = true) @PathVariable Long id) {
        return animeService.getAnimeById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Criar novo anime", description = "Cria um novo anime com as informações fornecidas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Anime criado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnimeDTO.class)))
    })
    @PostMapping
    public AnimeDTO createAnime(
            @Parameter(description = "Dados do anime a ser criado", required = true)
            @RequestBody AnimeDTO animeDTO) {
        Anime anime = convertToEntity(animeDTO);
        Anime savedAnime = animeService.createAnime(anime);
        return convertToDTO(savedAnime);
    }
    
    @Operation(summary = "Atualizar anime", description = "Atualiza um anime existente com base no ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Anime atualizado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnimeDTO.class))),
        @ApiResponse(responseCode = "404", description = "Anime não encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<AnimeDTO> updateAnime(
            @Parameter(description = "ID do anime a ser atualizado", required = true) @PathVariable Long id,
            @Parameter(description = "Novos dados do anime", required = true) @RequestBody AnimeDTO animeDTO) {
        Anime anime = convertToEntity(animeDTO);
        return animeService.updateAnime(id, anime)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Excluir anime", description = "Remove um anime com base no ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Anime excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Anime não encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnime(
            @Parameter(description = "ID do anime a ser excluído", required = true) @PathVariable Long id) {
        return animeService.deleteAnime(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    private Anime convertToEntity(AnimeDTO dto) {
        Anime anime = new Anime();
        // O id não deve ser definido na criação e será ignorado se for update
        // pois é controlado pelo parâmetro do método
        anime.setTitle(dto.getTitle());
        anime.setDescription(dto.getDescription());
        anime.setImageUrl(dto.getImageUrl());
        anime.setRating(dto.getRating());
        anime.setGenre(dto.getGenre());
        return anime;
    }

    private AnimeDTO convertToDTO(Anime anime) {
        AnimeDTO dto = new AnimeDTO();
        dto.setId(anime.getId());
        dto.setTitle(anime.getTitle());
        dto.setDescription(anime.getDescription());
        dto.setImageUrl(anime.getImageUrl());
        dto.setRating(anime.getRating());
        dto.setGenre(anime.getGenre());
        return dto;
    }
}