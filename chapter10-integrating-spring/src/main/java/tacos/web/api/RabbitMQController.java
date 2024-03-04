package tacos.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.data.OrderRepository;
import tacos.domain.TacoOrder;
import tacos.messaging.OrderMessagingService;
import tacos.messaging.rabbitmq.OrderReceiver;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/orders/messages/rabbitmq", produces = "application/json")
public class RabbitMQController {
  private final OrderMessagingService rabbitService;
  private final OrderRepository orderRepo;
  private final OrderReceiver orderReceiver;

  @PostMapping(consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public TacoOrder postOrderToBroker(@RequestBody TacoOrder order,
                                     @RequestParam(name = "placedInWeb", defaultValue = "false") boolean placedInWeb) {
    TacoOrder savedOrder = orderRepo.save(order);
    rabbitService.sendOrder(order, placedInWeb ? "WEB" : "STORE");
    return savedOrder;
  }

  @GetMapping
  public ResponseEntity<TacoOrder> receiveNewestOrder(@RequestParam(name = "timeout", defaultValue = "0") long timeout) {
//     Receive() method
//    Optional<TacoOrder> optOrder = Optional.ofNullable(orderReceiver.receiveOrder(timeout));

//    ReceiveAndConvertMethod()
   Optional<TacoOrder>  optOrder = Optional.ofNullable(orderReceiver.receiveOrder(timeout));
    return optOrder.map(order -> new ResponseEntity<>(order, HttpStatus.OK)).
        orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

  }
}
