package com.lux.animecollection.dto;

public class AnimeDTO {
    private String title;
    private String description;
    private String imageUrl;
    private String status;
    private Integer episodes;
    private Double rating;
    private String genre;
    private String type;
    private String releaseDate;

    // Construtor vazio
    public AnimeDTO() {
    }

    // Construtor com todos os campos
    public AnimeDTO(String title, String description, String imageUrl, String status, Integer episodes,
            Double rating, String genre, String type, String releaseDate) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.status = status;
        this.episodes = episodes;
        this.rating = rating;
        this.genre = genre;
        this.type = type;
        this.releaseDate = releaseDate;
    }

    // Getters e Setters
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
} 