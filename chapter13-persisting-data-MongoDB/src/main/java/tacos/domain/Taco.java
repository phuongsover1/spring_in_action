package tacos.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.*;

@Data
@RestResource(rel = "tacos", path = "tacos")
public class Taco {

    @Id
    private Long id;

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long!!!")
    private @NonNull String name;

    private Date createdAt  = new Date();

    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<Ingredient> ingredients = new ArrayList<>();

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }
}
