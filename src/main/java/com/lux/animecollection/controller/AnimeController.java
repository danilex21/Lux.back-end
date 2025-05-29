package com.lux.animecollection.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lux.animecollection.dto.AnimeDTO;
import com.lux.animecollection.model.Anime;
import com.lux.animecollection.service.AnimeService;
import com.lux.animecollection.service.FileStorageService;

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
    
    @Autowired
    private FileStorageService fileStorageService;
    
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
            @Parameter(description = "Título do anime", required = true) 
            @RequestParam("title") String title,
            
            @Parameter(description = "Descrição do anime") 
            @RequestParam(value = "description", required = false) String description,
            
            @Parameter(description = "Gênero do anime") 
            @RequestParam(value = "genre", required = false) String genre,
            
            @Parameter(description = "Classificação do anime") 
            @RequestParam(value = "rating", required = false) Double rating,
            
            @Parameter(description = "Arquivo de imagem do anime") 
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        
        try {
            AnimeDTO animeDTO = new AnimeDTO();
            animeDTO.setTitle(title);
            animeDTO.setDescription(description);
            animeDTO.setGenre(genre);
            animeDTO.setRating(rating != null ? rating : 0.0);
            
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = fileStorageService.storeFile(imageFile);
                animeDTO.setImageUrl(imageUrl);
            }
            
            Anime anime = convertToEntity(animeDTO);
            Anime savedAnime = animeService.createAnime(anime);
            return ResponseEntity.ok(convertToDTO(savedAnime));
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping(value = "/upload", 
                consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Upload de imagem", description = "Faz upload de uma imagem para o servidor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Imagem enviada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Nenhum arquivo enviado"),
        @ApiResponse(responseCode = "500", description = "Falha ao fazer upload da imagem")
    })
    public ResponseEntity<String> uploadImage(
            @Parameter(description = "Arquivo de imagem para upload (PNG, JPG, JPEG)", required = true)
            @RequestParam("file") MultipartFile file) {
        
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Por favor, selecione um arquivo para enviar.");
            }
            
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.badRequest().body("Por favor, envie apenas arquivos de imagem (PNG, JPG, JPEG).");
            }
            
            String imageUrl = fileStorageService.storeFile(file);
            return ResponseEntity.ok(imageUrl);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                .body("Falha ao fazer upload da imagem: " + e.getMessage());
        }
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

    protected Anime convertToEntity(AnimeDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Anime anime = new Anime();
        anime.setTitle(dto.getTitle());
        anime.setDescription(dto.getDescription());
        anime.setImageUrl(dto.getImageUrl());
        
        Double animeRating = dto.getRating();
        anime.setRating(animeRating != null ? animeRating : 0.0);
        
        anime.setGenre(dto.getGenre());
        return anime;
    }

    protected AnimeDTO convertToDTO(Anime anime) {
        if (anime == null) {
            return null;
        }
        
        AnimeDTO animeDto = new AnimeDTO();
        animeDto.setId(anime.getId());
        animeDto.setTitle(anime.getTitle());
        animeDto.setDescription(anime.getDescription());
        animeDto.setImageUrl(anime.getImageUrl());
        
        Double animeRating = anime.getRating();
        animeDto.setRating(animeRating != null ? animeRating : 0.0);
        
        animeDto.setGenre(anime.getGenre());
        return animeDto;
    }
}