package tacos.web.api;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tacos.data.IngredientRepository;
import tacos.domain.Ingredient;

import java.util.Optional;

@Data
@RestController
@RequestMapping(value = "/ingredients", produces = "application/json")
public class IngredientRestController {
  private final IngredientRepository ingreRepo;

  @GetMapping("/{id}")
  public ResponseEntity<Ingredient> getById(@PathVariable String id) {
    Optional<Ingredient> optIngre = ingreRepo.findById(id);
    return optIngre.map(ingredient -> new ResponseEntity<>(ingredient, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
  }
}
