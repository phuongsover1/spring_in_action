package com.example.developingtheclient.services;

import com.example.developingtheclient.domain.Ingredient;

public interface IngredientService {
  Iterable<Ingredient> findAll();

  Ingredient addIngredient(Ingredient ingredient);
}
