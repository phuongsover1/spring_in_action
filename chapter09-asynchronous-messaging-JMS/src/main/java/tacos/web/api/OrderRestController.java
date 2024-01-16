package tacos.web.api;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.data.OrderRepository;
import tacos.domain.TacoOrder;
import tacos.messaging.OrderMessagingService;

import java.util.Optional;

@Slf4j
@Data
@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping(path = "/api/orders", produces = "application/json")
public class OrderRestController {
  private final OrderRepository orderRepo;
  private final OrderMessagingService messagingService;

  @PutMapping(path = "/{orderId}", consumes = "application/json")
  public TacoOrder putOrder(@PathVariable("orderId") Long orderId, @RequestBody TacoOrder order) {
    order.setId(orderId);
    return orderRepo.save(order);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<TacoOrder> orderById(@PathVariable("orderId") Long orderId) {
    Optional<TacoOrder> optOrder = orderRepo.findById(orderId);
    return optOrder.map(order -> new ResponseEntity<>(order, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
  }

  @PatchMapping(path = "/{orderId}", consumes = "application/json")
  public TacoOrder patchOrder(@PathVariable("orderId") Long orderId, @RequestBody TacoOrder patch) {
    TacoOrder order = orderRepo.findById(orderId).get();
    if (patch.getDeliveryName() != null)
      order.setDeliveryName((patch.getDeliveryName()));
    if (patch.getDeliveryCity() != null)
      order.setDeliveryCity(patch.getDeliveryCity());
    if (patch.getDeliveryState() != null)
      order.setDeliveryState(patch.getDeliveryState());
    if (patch.getDeliveryZip() != null)
      order.setDeliveryZip(patch.getDeliveryZip());
    if (patch.getCcNumber() != null)
      order.setCcNumber(patch.getCcNumber());
    if (patch.getCcExpiration() != null)
      order.setCcExpiration(patch.getCcExpiration());
    if (patch.getCcCVV() != null)
      order.setCcCVV(patch.getCcCVV());
    return orderRepo.save(order);
  }

  @DeleteMapping("/{orderId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteOrder(@PathVariable("orderId")Long id) {
    try {
      orderRepo.deleteById(id);
    } catch (EmptyResultDataAccessException e) {

    }

  }

  @PostMapping(consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public TacoOrder postOrder(@RequestBody TacoOrder order) {
    messagingService.sendOrder(order);
    return orderRepo.save(order);
  }
}
