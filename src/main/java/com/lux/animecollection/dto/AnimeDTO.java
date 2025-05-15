package com.lux.animecollection.dto;

public class AnimeDTO {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private Double rating;
    private String genre;

    // Construtor vazio
    public AnimeDTO() {
    }

    // Construtor com todos os campos
    public AnimeDTO(Long id, String title, String description, String imageUrl, Double rating, String genre) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.genre = genre;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
} 