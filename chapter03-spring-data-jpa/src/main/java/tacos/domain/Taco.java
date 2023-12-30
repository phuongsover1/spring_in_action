package tacos.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import tacos.IngredientRef;

@Data
@Table
public class Taco {

  @Id
  private Long id;
  
  private Date createdAt = new Date();

  @NotNull
  @Size(min = 5, message = "Name must be at least 5 characters long!!!")
  private String name;

  @NotNull(message = "You must choose at least 1 ingredient")
  @Size(min = 1, message = "You must choose at least 1 ingredient")
  private List<IngredientRef> ingredients;

  public void addIngredient(Ingredient taco) {
    this.ingredients.add(new IngredientRef(taco.getId()));
  }

}
