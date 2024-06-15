package tacos.data;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import tacos.domain.Ingredient;

import java.util.ArrayList;

@DataR2dbcTest
class IngredientRepositoryTest {

    @Autowired
    IngredientRepository repo;

    @BeforeEach
    public void setup() {
        Flux<Ingredient> deleteAndInsert = repo.deleteAll()
                .thenMany(repo.saveAll(
                        Flux.just(
                                new Ingredient("FLTO", "FlourTortilla", Ingredient.Type.WRAP),
                                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
                                new Ingredient("CHED", "Cheddar Cheese", Ingredient.Type.CHEESE)
                        )
                ));

        StepVerifier.create(deleteAndInsert)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void shouldSaveAndFetchIngredients() {
        StepVerifier.create(repo.findAll())
                .recordWith(ArrayList::new)
                .thenConsumeWhile(x -> true)
                .consumeRecordedWith(ingredients -> {
                    Assertions.assertThat(ingredients).hasSize(3);
                    Assertions.assertThat(ingredients).contains(
                            new Ingredient("FLTO", "FlourTortilla", Ingredient.Type.WRAP),
                            new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
                            new Ingredient("CHED", "Cheddar Cheese", Ingredient.Type.CHEESE)
                    );
                })
                .verifyComplete();

        StepVerifier.create(repo.findBySlug("FLTO"))
                .assertNext(ingredient -> {
                    ingredient.equals(new Ingredient("FLTO", "FlourTortilla", Ingredient.Type.WRAP));
                })
                .verifyComplete();
    }

}