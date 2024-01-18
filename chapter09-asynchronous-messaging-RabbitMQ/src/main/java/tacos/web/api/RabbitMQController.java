package tacos.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tacos.data.OrderRepository;
import tacos.domain.TacoOrder;
import tacos.messaging.OrderMessagingService;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/orders/messages/rabbitmq")
public class RabbitMQController {
  private final OrderMessagingService rabbitService;
  private final OrderRepository orderRepo;

  @PostMapping(consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public TacoOrder postOrderToBroker(@RequestBody TacoOrder order, @RequestParam(name = "placedInWeb",defaultValue = "false")boolean placedInWeb) {
    TacoOrder savedOrder = orderRepo.save(order);
    rabbitService.sendOrder(order, placedInWeb ? "WEB" : "STORE");
    return savedOrder;
  }
}
