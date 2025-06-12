package com.example.recipeapp;

import org.springfrmework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByCuisine(String cuisine);
    List<Recipe> findByNameContainingIgnoreCaseOrIngredientsContainingIgnoreCase(String name, String ingredients);
}