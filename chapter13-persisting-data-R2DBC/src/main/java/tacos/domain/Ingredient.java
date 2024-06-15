package tacos.domain;


import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@EqualsAndHashCode(exclude = "id")
public class Ingredient {
  @Id
  private Long id;
  private @NonNull String slug;

  private @NonNull String name;
  private @NonNull Type type;

  public enum Type {
    WRAP,
    PROTEIN,
    VEGGIES,
    CHEESE,
    SAUCE
  }
}
