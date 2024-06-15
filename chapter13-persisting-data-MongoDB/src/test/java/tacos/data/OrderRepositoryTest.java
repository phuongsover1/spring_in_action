package tacos.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tacos.domain.Ingredient;
import tacos.domain.Taco;
import tacos.domain.TacoOrder;

@DataMongoTest
class OrderRepositoryTest {
    @Autowired
    OrderRepository orderRepo;

    @Autowired
    IngredientRepository ingredientRepo;

    @BeforeEach
    public void setup() {
        orderRepo.deleteAll().subscribe();
    }

    @Test
    public void shouldSaveAndFetchOrder() {
        TacoOrder order = createOrder();


        StepVerifier.create(orderRepo.save(order))
                .expectNext(order)
                .verifyComplete();

        StepVerifier.create(orderRepo.findById(order.getId()))
                .expectNext(order)
                .verifyComplete();

        StepVerifier.create(orderRepo.findAll())
                .expectNext(order)
                .verifyComplete();
    }

    @Test
    public void fetchIngre() {
        StepVerifier.create(ingredientRepo.findById("FLTO"))
                .expectNext(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP))
                .verifyComplete();
    }

    private TacoOrder createOrder() {
        TacoOrder newOrder = new TacoOrder();

        newOrder.setDeliveryName("Test Customer");
        newOrder.setDeliveryStreet("1234 North Street");
        newOrder.setDeliveryCity("Notrees");
        newOrder.setDeliveryState("TX");
        newOrder.setDeliveryZip("12345");
        newOrder.setCcNumber("4111111111111111");
        newOrder.setCcExpiration("12/24");
        newOrder.setCcCVV("123");

        Taco taco1 = new Taco("Test Taco One");

        return ingredientRepo.findAll()
                .flatMap(ingre -> {
                    taco1.addIngredient(ingre);
                    return Mono.just(taco1);
                })
                .last()
                .map(taco -> {
                    newOrder.addTaco(taco);
                    return newOrder;
                }).block();
    }
}