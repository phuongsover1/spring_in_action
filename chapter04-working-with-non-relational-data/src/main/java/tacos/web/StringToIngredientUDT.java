package tacos.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import tacos.data.IngredientRepository;
import tacos.domain.Ingredient;
import tacos.domain.IngredientUDT;

@Component
@RequiredArgsConstructor
public class StringToIngredientUDT implements Converter<String, IngredientUDT> {

    private final IngredientRepository repo;

    @Override
    public IngredientUDT convert(String source) {
        Ingredient ingredient = repo.findById(source).orElse(null);
        return ingredient == null ? null : new IngredientUDT(ingredient.getName(), ingredient.getType());

    }

}
