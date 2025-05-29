package com.lux.animecollection.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Base64;

@Data
@Entity
@Table(name = "animes")
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "title", length = 255, nullable = false)
    private String title;
    
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imageData;
    
    @Column(name = "image_type", length = 100)
    private String imageType;
    
    private Double rating = 0.0;
    
    @Column(length = 255)
    private String genre;
    
    public Anime() {
    }

    public Anime(Long id, String title, String description, byte[] imageData, String imageType, Double rating, String genre) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageData = imageData;
        this.imageType = imageType;
        this.rating = rating;
        this.genre = genre;
    }

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
        if (imageData == null || imageType == null) {
            return null;
        }
        return "data:" + imageType + ";base64," + Base64.getEncoder().encodeToString(imageData);
    }

    public void setImageUrl(String imageUrl) {
        if (imageUrl != null && imageUrl.contains(",")) {
            String[] parts = imageUrl.split(",");
            if (parts.length > 1) {
                this.imageData = Base64.getDecoder().decode(parts[1]);
                if (parts[0].contains(":") && parts[0].contains(";")) {
                    this.imageType = parts[0].substring(parts[0].indexOf(":") + 1, parts[0].indexOf(";"));
                }
            }
        }
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating != null ? rating : 0.0;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}
