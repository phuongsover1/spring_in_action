package tacos;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tacos.domain.Ingredient;
import tacos.domain.Taco;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TacoControllerWebTest {
    @Autowired
    private WebTestClient webTestClient;


    @Test
    public void shouldReturnRecentTacos() {
        webTestClient.get().uri("/api/tacos?recent")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$.length()").isEqualTo(3)
                .jsonPath("$[?(@.name == 'Taco1')]").exists()
                .jsonPath("$[?(@.name == 'Taco2')]").exists()
                .jsonPath("$[?(@.name == 'Taco3')]").exists();
    }

    @Test
    public void consumeTaco() {
        Mono<Taco> tacoMono = WebClient.create()
                .get()
                .uri("http://localhost:8080/api/tacos/{id}", 1L)
                .retrieve()
                .bodyToMono(Taco.class);

        tacoMono.subscribe(i -> {
            Assertions.assertEquals("Taco1", i.getName());
            Assertions.assertEquals(1L, i.getId());
        });
    }
}
