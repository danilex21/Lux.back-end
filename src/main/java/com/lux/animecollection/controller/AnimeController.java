package com.lux.animecollection.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Base64;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
@CrossOrigin(origins = {"http://localhost:8081", "https://anime-collection-nf6r.onrender.com"}, 
    allowedHeaders = "*", 
    allowCredentials = "true",
    maxAge = 3600)
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
    
    @Operation(summary = "Obter imagem do anime", description = "Retorna a imagem de um anime específico")
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getAnimeImage(@PathVariable Long id) {
        Optional<Anime> animeOptional = animeService.getAnimeById(id);
        if (animeOptional.isPresent() && animeOptional.get().getImageData() != null) {
            Anime anime = animeOptional.get();
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(anime.getImageType()))
                    .body(anime.getImageData());
        }
        return ResponseEntity.notFound().build();
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
    
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Criar novo anime", description = "Cria um novo anime com as informações fornecidas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Anime criado com sucesso",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = AnimeDTO.class))),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<AnimeDTO> createAnime(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam(value = "genre", required = false) String genre,
            @RequestParam(value = "rating", required = false) Double rating,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        
        try {
            Anime anime = new Anime();
            anime.setTitle(title);
            anime.setDescription(description);
            anime.setGenre(genre);
            anime.setRating(rating != null ? rating : 0.0);
            
            if (imageFile != null && !imageFile.isEmpty()) {
                anime.setImageData(imageFile.getBytes());
                anime.setImageType(imageFile.getContentType());
            }
            
            Anime savedAnime = animeService.createAnime(anime);
            return ResponseEntity.ok(convertToDTO(savedAnime));
            
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping(value = "/{id}/image", 
                consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Upload de imagem", description = "Faz upload de uma imagem para um anime existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Imagem enviada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Nenhum arquivo enviado"),
        @ApiResponse(responseCode = "404", description = "Anime não encontrado"),
        @ApiResponse(responseCode = "500", description = "Falha ao fazer upload da imagem")
    })
    public ResponseEntity<AnimeDTO> uploadImage(
            @Parameter(description = "ID do anime", required = true) @PathVariable Long id,
            @Parameter(description = "Arquivo de imagem para upload (PNG, JPG, JPEG)", required = true)
            @RequestParam("file") MultipartFile file) {
        
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        try {
            Optional<Anime> animeOpt = animeService.getAnimeById(id);
            if (!animeOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            Anime anime = animeOpt.get();
            anime.setImageData(file.getBytes());
            anime.setImageType(file.getContentType());
            
            Anime updatedAnime = animeService.updateAnime(id, anime);
            if (updatedAnime != null) {
                return ResponseEntity.ok(convertToDTO(updatedAnime));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Atualizar anime", description = "Atualiza um anime existente com base no ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Anime atualizado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnimeDTO.class))),
        @ApiResponse(responseCode = "404", description = "Anime não encontrado", content = @Content)
    })
    public ResponseEntity<AnimeDTO> updateAnime(
            @Parameter(description = "ID do anime a ser atualizado", required = true) @PathVariable Long id,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "genre", required = false) String genre,
            @RequestParam(value = "rating", required = false) Double rating,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        
        try {
            Optional<Anime> existingAnimeOpt = animeService.getAnimeById(id);
            if (!existingAnimeOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            Anime existingAnime = existingAnimeOpt.get();
            if (title != null) existingAnime.setTitle(title);
            if (description != null) existingAnime.setDescription(description);
            if (genre != null) existingAnime.setGenre(genre);
            if (rating != null) existingAnime.setRating(rating);
            
            if (imageFile != null && !imageFile.isEmpty()) {
                existingAnime.setImageData(imageFile.getBytes());
                existingAnime.setImageType(imageFile.getContentType());
            }
            
            Anime updatedAnime = animeService.updateAnime(id, existingAnime);
            if (updatedAnime != null) {
                return ResponseEntity.ok(convertToDTO(updatedAnime));
            } else {
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
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

    protected AnimeDTO convertToDTO(Anime anime) {
        if (anime == null) {
            return null;
        }
        
        AnimeDTO dto = new AnimeDTO();
        dto.setId(anime.getId());
        dto.setTitle(anime.getTitle());
        dto.setDescription(anime.getDescription());
        dto.setRating(anime.getRating() != null ? anime.getRating() : 0.0);
        dto.setGenre(anime.getGenre());
        
        // If there's image data, create a data URL
        if (anime.getImageData() != null && anime.getImageType() != null) {
            String base64Image = Base64.getEncoder().encodeToString(anime.getImageData());
            String imageUrl = "data:" + anime.getImageType() + ";base64," + base64Image;
            dto.setImageUrl(imageUrl);
        }
        
        return dto;
    }

    protected Anime convertToEntity(AnimeDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Anime anime = new Anime();
        anime.setId(dto.getId());
        anime.setTitle(dto.getTitle());
        anime.setDescription(dto.getDescription());
        anime.setRating(dto.getRating() != null ? dto.getRating() : 0.0);
        anime.setGenre(dto.getGenre());
        
        // Note: We don't handle image data here as it should be set via file upload
        return anime;
    }
}