package tacos.consuming_rest;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

  @GetMapping("/{id}")
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

}