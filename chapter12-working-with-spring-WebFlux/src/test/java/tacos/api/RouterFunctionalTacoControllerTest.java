package tacos.api;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import tacos.data.TacoRepository;
import tacos.domain.Ingredient;
import tacos.domain.Taco;
import tacos.web.TacoProp;
import tacos.web.api.TacoController;

import java.util.ArrayList;
import java.util.List;

public class RouterFunctionalTacoControllerTest {
    @Test
    public void shouldReturnRecentTacos() {
        Taco[] tacos = {
                testTaco(1L), testTaco(2L),
                testTaco(3L), testTaco(4L),
                testTaco(5L), testTaco(6L),
                testTaco(7L), testTaco(8L),
                testTaco(9L), testTaco(10L),
                testTaco(11L), testTaco(12L),
                testTaco(13L), testTaco(14L),
                testTaco(15L), testTaco(16L),
        };

        TacoRepository tacoRepository = Mockito.mock(TacoRepository.class);
        TacoProp tacoProp = new TacoProp();
        Mockito.when(tacoRepository.findAll()).thenReturn(List.of(tacos));

        WebTestClient testClient = WebTestClient
                .bindToController(new TacoController(tacoRepository, tacoProp))
                .build();

        testClient.get().uri("/api/tacos?recent")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$").isNotEmpty()
                .jsonPath("$.length()").isEqualTo(12)
                .jsonPath("$[0].id").isEqualTo(tacos[0].getId().toString())
                .jsonPath("$[0].name").isEqualTo("Taco 1")
                .jsonPath("$[1].id").isEqualTo(tacos[1].getId().toString())
                .jsonPath("$[1].name").isEqualTo("Taco 2")
                .jsonPath("$[11].id").isEqualTo(tacos[11].getId().toString())
                .jsonPath("$[11].name").isEqualTo("Taco 12")
                .jsonPath("$[12]").doesNotExist();
    }

    private Taco testTaco(Long id) {
        Taco taco = new Taco();
        taco.setId(id != null ? id : 1L);
        taco.setName("Taco " + id);
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("INGA", "Ingredient A", Ingredient.Type.WRAP));
        ingredients.add(new Ingredient("INGB", "Ingredient B", Ingredient.Type.PROTEIN));
        taco.setIngredients(ingredients);
        return taco;
    }
}
