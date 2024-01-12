package tacos.consuming_rest;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tacos.domain.Ingredient;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
@RestController
@RequestMapping(value = "/consuming-ingredients", produces = "application/json")
public class ConsumingIngredientController {
  private final RestTemplate restTemplate;

  @GetMapping("/getForObject/{id}")
  public ResponseEntity<Ingredient> getIngredientById(@PathVariable String id) {
    Map<String, String> urlVariables = new HashMap<>();
    urlVariables.put("id", id);
    URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/data-api/ingredients/{id}")
            .build(urlVariables);
    try {
      Ingredient ingredient = restTemplate.getForObject(url, Ingredient.class);
      if (ingredient != null)
        return new ResponseEntity<>(ingredient, HttpStatus.OK);

    } catch (RestClientException ex) {
    }
    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }

  @GetMapping("/getForEntity/{id}")
  public Ingredient getIngredientByIdUsingGetForEntity(@PathVariable String id) {

    ResponseEntity<Ingredient> responseEntity = restTemplate.getForEntity("http://localhost:8080/data-api/ingredients/{id}",
            Ingredient.class,
            id);
    log.info("Fetched time: {}", responseEntity.getHeaders().getDate());
    return responseEntity.getBody();

  }

  @PutMapping(value = "/{id}", consumes = "application/json")
  public void updateIngredientUsingPut(@PathVariable String id, @RequestBody Ingredient ingredient) {
    restTemplate.put("http://localhost:8080/data-api/ingredients/{id}", ingredient, id);
  }

  @DeleteMapping("/{id}")
  public void deleteIngredient(@PathVariable String id) {
    restTemplate.delete("http://localhost:8080/data-api/ingredients/{id}", id);
  }

  @PostMapping(value = "/returnCreatedObj", consumes = "application/json")
  public Ingredient postAndReturnCreatedIngredient(@RequestBody Ingredient ingredient) {
   return restTemplate.postForObject("http://localhost:8080/data-api/ingredients", ingredient, Ingredient.class);
  }

  @PostMapping(value = "/returnCreatedObjUrlLocation", consumes = "application/json")
  public URI postAndReturnCreatedObjLocation(@RequestBody Ingredient ingredient) {
    return restTemplate.postForLocation("http://localhost:8080/data-api/ingredients", ingredient);
  }

  @PostMapping(value = "/returnCreatedObjAndLocation", consumes = "application/json")
  public Ingredient postAndReturnCreatedObjAndLocation(@RequestBody Ingredient ingredient) {
    ResponseEntity<Ingredient> response = restTemplate.postForEntity("http://localhost:8080/data-api/ingredients",
            ingredient,
            Ingredient.class);
    log.info("Newly Created Obj Location: " +  response.getHeaders().getLocation());

    return response.getBody();

  }

}
