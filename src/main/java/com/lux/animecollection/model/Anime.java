package com.lux.animecollection.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 255)
    private String title;
    
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    
    @Column(length = 1000)
    private String imageUrl;
    
    @Column(length = 50)
    private String status;
    
    private Integer episodes;
    private Double rating;
    
    @Column(length = 255)
    private String genre;
    
    @Column(length = 50)
    private String type;
    
    @Column(length = 50)
    private String releaseDate;
} 