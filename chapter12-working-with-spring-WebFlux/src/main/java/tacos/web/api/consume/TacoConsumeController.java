package tacos.web.api.consume;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tacos.domain.Taco;

import java.time.Duration;

@RestController
@RequestMapping(path = "/api/consume/tacos", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class TacoConsumeController {
    private final WebClient webClient;

    @GetMapping("/{id}")
    public Mono<Taco> consumeTaco(@PathVariable Long id) {
        Mono<Taco> foundTaco = webClient.get().uri("/api/tacos/{id}", id)
                .retrieve()
                .bodyToMono(Taco.class);
        return foundTaco.timeout(Duration.ofSeconds(1))
                .onErrorReturn(Taco.builder().id(-1L).name("DEFAULT TACO").build());

    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Taco> saveTacoOnExternalAPI(@RequestBody Mono<Taco> tacoMono) {
        return webClient.post()
                .uri("/api/tacos")
                .accept(MediaType.APPLICATION_JSON)
                .body(tacoMono, Taco.class)
                .retrieve()
                .bodyToMono(Taco.class)
                .timeout(Duration.ofSeconds(1))
                .onErrorReturn(Taco.builder().id(-1L).name("DEFAULT TACO").build());
    }


    @PutMapping(path = "/{id}",consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> updateTacoOnExternalAPI(@PathVariable Long id,  @RequestBody Mono<Taco> updateTaco) {
       return webClient
               .put().uri("/api/tacos/{id}", id)
               .body(updateTaco, Taco.class)
               .retrieve()
               .bodyToMono(Void.class);

    }
}
