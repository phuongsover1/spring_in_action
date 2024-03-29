package tacos.web.api;

import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.data.OrderRepository;
import tacos.domain.TacoOrder;
import tacos.messaging.jms.sender.OrderMessagingService;
import tacos.messaging.jms.sender.OrderReceive;

import java.util.Optional;

@Slf4j
@Data
@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping(path = "/api/orders", produces = "application/json")
public class OrderRestController {
  private final OrderRepository orderRepo;
  private final OrderMessagingService messagingService;
  private final Destination customDestination;
  private final OrderReceive jmsOrderReceiver;

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
  public void deleteOrder(@PathVariable("orderId") Long id) {
    try {
      orderRepo.deleteById(id);
    } catch (EmptyResultDataAccessException e) {

    }

  }

  @PostMapping(consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public TacoOrder postOrder(@RequestBody TacoOrder order, @RequestParam("useCustomQueue") boolean useCustomQueue) {
    if (!useCustomQueue)
      messagingService.sendOrder(order);
    else
      messagingService.sendOrder(customDestination, order);
    return orderRepo.save(order);
  }

  @PostMapping(value = "/convertAndSend", consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public TacoOrder postOrder1(@RequestBody TacoOrder order,
                              @RequestParam(defaultValue = "false") boolean useCustomQueue,
                              @RequestParam(name = "orderInWeb", required = true) boolean orderInWeb) {
    TacoOrder savedOrder =  orderRepo.save(order);
    if (!useCustomQueue)
      if (!orderInWeb)
        messagingService.convertAndSend(savedOrder, "STORE");
      else
        messagingService.convertAndSend(savedOrder, "WEB");
    else {
      if (!orderInWeb)
        messagingService.convertAndSend(customDestination, savedOrder, "STORE");
      else
        messagingService.convertAndSend(customDestination, savedOrder, "WEB");
    }
  return savedOrder;
  }

  @GetMapping("/messages")
  public TacoOrder receiveMessageFromBroker() throws JMSException {
     return jmsOrderReceiver.receiveOrder();
  }

  @GetMapping("/messages/receiveAndConvert")
  public TacoOrder receiveAndConvertTacoOrderFromMessage() throws  JMSException {
    return jmsOrderReceiver.receiveAndConvertOrder();
  }
}
