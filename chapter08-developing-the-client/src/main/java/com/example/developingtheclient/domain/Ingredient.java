package com.example.developingtheclient.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
  private String id;
  private String name;
  private Type type;

  public static enum Type {
    WRAP,
    PROTEIN,
    VEGGIES,
    CHEESE,
    SAUCE
  }
}
