package tacos.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Document
public class Ingredient implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  private String id;
  private String name;
  private Type type;

  public enum Type {
    WRAP,
    PROTEIN,
    VEGGIES,
    CHEESE,
    SAUCE
  }
}
