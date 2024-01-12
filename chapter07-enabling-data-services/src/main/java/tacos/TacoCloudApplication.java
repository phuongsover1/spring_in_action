package tacos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;
import tacos.data.IngredientRepository;
import tacos.data.OrderRepository;
import tacos.data.TacoRepository;
import tacos.domain.Ingredient;
import tacos.domain.Ingredient.Type;
import tacos.domain.Taco;
import tacos.domain.TacoOrder;

import java.util.Arrays;
import java.util.Date;

@Slf4j
@SpringBootApplication
public class TacoCloudApplication {

  public static void main(String[] args) {
    SpringApplication.run(TacoCloudApplication.class, args);
  }

  @Bean
  @Profile("test")
//	@Profile({"prod", "test"})
//	@Profile("!prod")
  public CommandLineRunner dataLoader(IngredientRepository repo, TacoRepository tacoRepo, OrderRepository orderRepo) {
    return args -> {
      Ingredient FLTO = repo.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
      Ingredient COTO = repo.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
      Ingredient GRBF = repo.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
      Ingredient CARN = repo.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
      Ingredient TMTO = repo.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
      Ingredient LETC = repo.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
      Ingredient CHED = repo.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
      Ingredient JACK = repo.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
      Ingredient SLSA = repo.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
      Ingredient SRCR = repo.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));

      Taco taco1 = new Taco();
//      taco1.setId(1L);
      taco1.setName("Taco1");
      taco1.setIngredients(Arrays.asList(FLTO, GRBF, CARN, SRCR, SLSA, CHED));
      taco1 = tacoRepo.save(taco1);

      Taco taco2 = new Taco();
//      taco2.setId(2L);
      taco2.setName("Taco2");
      taco2.setIngredients(Arrays.asList(COTO, GRBF, CHED, JACK, SRCR));
      taco2 = tacoRepo.save(taco2);

      Taco taco3 = new Taco();
//      taco3.setId(3L);
      taco3.setName("Taco3");
      taco3.setIngredients(Arrays.asList(FLTO, COTO, TMTO, LETC));
      taco3 = tacoRepo.save(taco3);

      TacoOrder order1 = new TacoOrder();
      order1.setId(1L);
      order1.setPlacedAt(new Date());
      order1.setCcCVV("123");
      order1.setCcNumber("374245455400126");
      order1.setDeliveryCity("HO CHI MINH");
      order1.setDeliveryName("VNPT EXPRESS");
      order1.setDeliveryStreet("TAN BINH");
      order1.setDeliveryState("VN");
      order1.setCcExpiration("12/23");
      order1.setDeliveryZip("123");
      order1.addTaco(taco1);
      order1.addTaco(taco2);
      orderRepo.save(order1);

    };
  }


  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
