package tacos.web.api.consume;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tacos.domain.Taco;

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
        return foundTaco;

    }
}
