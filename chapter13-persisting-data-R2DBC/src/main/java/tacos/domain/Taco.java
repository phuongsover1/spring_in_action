package tacos.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Taco {

  @Id
  private Long id;
  
  private Date createdAt = new Date();

  @NotNull
  @Size(min = 5, message = "Name must be at least 5 characters long!!!")
  private @NonNull String name;

  private Set<Long> ingredientIds = new HashSet<>();

  public void addIngredient(Ingredient ingredient) {
    ingredientIds.add(ingredient.getId());
  }

}
