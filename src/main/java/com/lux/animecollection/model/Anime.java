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
    
    @Column(name = "titulo", length = 255)
    private String title;
    
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    
    @Column(length = 1000)
    private String imageUrl;
    
    private Double rating;
    
    @Column(length = 255)
    private String genre;
    
    
    

	public Anime() {
	}

	

	public Anime(Long id, String title, String description, String imageUrl, Double rating, String genre) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.imageUrl = imageUrl;
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
