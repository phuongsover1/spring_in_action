package tacos.domain;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Taco {

@Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  
  private Date createdAt = new Date();

  @NotNull
  @Size(min = 5, message = "Name must be at least 5 characters long!!!")
  private String name;

  @NotNull(message = "You must choose at least 1 ingredient")
  @Size(min = 1, message = "You must choose at least 1 ingredient")
  @ManyToMany()
  private List<Ingredient> ingredients;

  public void addIngredient(Ingredient taco) {
    this.ingredients.add(taco);
  }

}
