package tacos.web.api;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
  public Iterable<Taco> recentTacos() {
    PageRequest page = PageRequest.of(0, tacoProp.getPageSize(), Sort.by("createdAt").descending());
    return tacoRepo.findAllBy(page);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
    Optional<Taco> optTaco = tacoRepo.findById(id);

    if (optTaco.isPresent())
      return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }
}
