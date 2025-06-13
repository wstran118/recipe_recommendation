package com.example.recipeapp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Reciple name is required")
    @Size(max =  100, message  = "Recipe name must be less than 100 characters")
    private String name;

    @NotBlank(message = "Ingredients are required")
    @Size(max = 2000, message = "Instructions must be less than 2000 characters")
    private String instructions;

    @NotBlank(message = "Cuisine is required")
    @Size(max =  50, message  = "Cuisine must be less than 50 characters")
    private String cuisine;

    private double averageRating;
    private int ratingCount;

    @NotBlank(message = "Created by is required")
    private String createdBy;

    //Getter and setters
    public long getId() return id;

    public void setId(long id) this.id = id;

    public String getName() return name;

    public void setName(String name) this.name = name;

    public String getIngredients() return ingredients;

    public void setIngredients(String ingredients) this.ingredients = ingredients;

    public String getInstructions() return instructions;

    public void setInstructions() this.instructions = instructions;

    public String getCuisine() return cuisine;

    public void setCuisine(String cuisine) this.cuisine = cuisine;

    public double getAverageRating() return averageRating;

    public void setAverageRating(double averageRating) this.averageRating = averageRating;

    public int getRatingCount() return ratingCount;

    public void setRatingCount(int ratingCount) this.ratingCount = ratingCount;

    public String getCreatedBy() return createdBy;

    public void setCreatedBy(String createdBy) this.createdBy = createdBy;
}