package tacos.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;
import tacos.data.IngredientRepository;
import tacos.domain.Ingredient;
import tacos.domain.Ingredient.Type;
import tacos.domain.Taco;
import tacos.domain.TacoOrder;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

  private final IngredientRepository ingredientRepository;

  public DesignTacoController(IngredientRepository ingredientRepository) {
    this.ingredientRepository = ingredientRepository;
  }

  @ModelAttribute
  public void addIngredientsToModel(Model model) {
    Iterable<Ingredient> ingredients = ingredientRepository.findAll();
    Type[] types = Ingredient.Type.values();
    for (Type type : types) {
      model.addAttribute(type.toString().toLowerCase(),
          filterByType(StreamSupport.stream(ingredients.spliterator(), true).toList(), type));
    }
  }

  @ModelAttribute(name = "tacoOrder")
  public TacoOrder order() {
    log.info("In model attribute: tacoOrder");
    return new TacoOrder();
  }

  @ModelAttribute(name = "taco")
  public Taco taco() {
    log.info("In model attribute: taco");
    return new Taco();
  }

  @GetMapping
  public String showDesignForm() {
    return "design";
  }

  @PostMapping
  public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute TacoOrder tacoOrder) {
    // Save the taco design
    // We'll do this in chapter 3
    log.info("Processing design: " + design);

    if (errors.hasErrors())
      return "design";
    tacoOrder.addTaco(design);
    return "redirect:/orders/current";
  }

  private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
    return ingredients.stream()
        .filter(it -> it.getType().equals(type))
        .collect(Collectors.toList());
  }
}
