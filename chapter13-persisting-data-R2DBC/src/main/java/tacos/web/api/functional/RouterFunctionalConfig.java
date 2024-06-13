package tacos.web.api.functional;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.data.TacoRepository;
import tacos.domain.Taco;
import tacos.web.TacoProp;

import java.net.URI;

@RequiredArgsConstructor
@Configuration
public class RouterFunctionalConfig {

    private final TacoRepository tacoRepository;

    @Bean
    public RouterFunction<?> routerFunction() {
        return RouterFunctions.route(RequestPredicates.GET("/api/functional/tacos")
                        .and(RequestPredicates.queryParam("recent", t -> t != null)),
                this::recents)
                .andRoute(RequestPredicates.POST("/api/functional/tacos"),this::postTaco);
    }

    public Mono<ServerResponse> recents(ServerRequest request) {
        return ServerResponse.ok().body(Flux.fromIterable(tacoRepository.findAll()).take(12), Taco.class);
    }

    public Mono<ServerResponse> postTaco(ServerRequest request) {
        return request.bodyToMono(Taco.class)
                .flatMap(taco -> Mono.just(tacoRepository.save(taco)))
                .flatMap(savedTaco -> ServerResponse.created(URI.create(
                                "http://localhost:8080/api/tacos/" +
                                        savedTaco.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
//                        .bodyValue(savedTaco));
                        .body(Mono.just(savedTaco), Taco.class));

    }

}
