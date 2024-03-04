package com.example.developingtheclient.services;

import com.example.developingtheclient.domain.Ingredient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

@Service
@Data
public class RestIngredientService implements IngredientService {
  private final RestTemplate restTemplate;

  @Autowired
  public RestIngredientService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public RestIngredientService(String accessToken, RestTemplate restTemplate) {
    this(restTemplate);
    if (accessToken != null) {
      this.restTemplate
          .getInterceptors()
          .add(getBearerTokenInterceptor(accessToken));
    }
  }

  private ClientHttpRequestInterceptor getBearerTokenInterceptor(String accessToken) {
    ClientHttpRequestInterceptor interceptor =
        new ClientHttpRequestInterceptor() {
          @Override
          public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            request.getHeaders().add("Authorization", "Bearer " + accessToken);
            return execution.execute(request, body);
          }
        };
    return interceptor;
  }

  @Override
  public Iterable<Ingredient> findAll() {
    return Arrays.asList(restTemplate.getForObject("http://localhost:8080/data-api/ingredients", Ingredient[].class));
  }

  @Override
  public Ingredient addIngredient(Ingredient ingredient) {
    return restTemplate.postForObject("http://localhost:8080/data-api/ingredients", ingredient, Ingredient.class);
  }
}
