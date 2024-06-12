package tacos.web.api;

import io.netty.util.internal.StringUtil;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.data.TacoRepository;
import tacos.domain.Taco;
import tacos.web.TacoProp;

import java.util.Optional;

@Data
@RestController
@RequestMapping(path = "/api/tacos", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public class TacoController {

  private final TacoRepository tacoRepo;
  private final TacoProp tacoProp;

  @GetMapping(params = "recent")
  public Flux<Taco> recentTacos() {
    return Flux.fromIterable(tacoRepo.findAll()).take(12);
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<Taco>> tacoById(@PathVariable("id") Long id) {
    Optional<Taco> optTaco = tacoRepo.findById(id);

    if (optTaco.isPresent())
      return Mono.just(new ResponseEntity<>(optTaco.get(), HttpStatus.OK));
    return Mono.just(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
  }

  @PostMapping(consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Taco> postTaco(@RequestBody Mono<Taco> tacoMono) {
    return tacoMono.flatMap(taco -> Mono.just(taco).map(tacoRepo::save));
  }

  @PutMapping(path = "/{id}", consumes = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> updateTaco(@PathVariable Long id, @RequestBody Mono<Taco> tacoMono) {
    return tacoMono.flatMap(t -> {
        Optional<Taco> tacoInDBOpt =  tacoRepo.findById(id);
        if (tacoInDBOpt.isPresent()){
          Taco taco = tacoInDBOpt.get();
          if (!StringUtil.isNullOrEmpty(t.getName()))
            taco.setName(t.getName());
          if(t.getCreatedAt() != null)
            taco.setCreatedAt(t.getCreatedAt());
          if (t.getIngredients() != null && !t.getIngredients().isEmpty())
            taco.setIngredients(t.getIngredients());
           tacoRepo.save(taco);
        }
        return Mono.empty();
      });
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteTaco(@PathVariable Long id) {
     tacoRepo.deleteById(id);
     return Mono.empty();

  }

}
