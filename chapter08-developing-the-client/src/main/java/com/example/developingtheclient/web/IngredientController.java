package com.example.developingtheclient.web;

import com.example.developingtheclient.domain.Ingredient;
import com.example.developingtheclient.services.IngredientService;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Data
@RequestMapping("/admin/ingredients")
public class IngredientController {
  private final IngredientService ingredientService;
  @PostMapping
  public String addIngredient(Ingredient ingredient) {
    ingredientService.addIngredient(ingredient);
    return "redirect:/admin/ingredients";
  }

  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("ingredients", ingredientService.findAll());
    return "ingredientsAdmin";
  }

}
