package tacos.api;

import io.netty.util.internal.StringUtil;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import tacos.data.OrderRepository;
import tacos.domain.TacoOrder;

import java.util.Date;
import java.util.Objects;

@Slf4j
@Data
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/api/orders", produces = "application/json")
public class OrderRestController {
    private final OrderRepository orderRepo;

    @PutMapping(path = "/{orderId}", consumes = "application/json")
    public Mono<ResponseEntity<Void>> putOrder(@PathVariable("orderId") String orderId, @RequestBody TacoOrder order) {

        return orderRepo
                .findById(orderId)
                .log()
                .switchIfEmpty(Mono.just(TacoOrder.empty))
                .map(o -> {
                    if (Objects.equals(o, TacoOrder.empty))
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    orderRepo.save(checkFieldWhenUpdate(o, order)).log().subscribeOn(Schedulers.parallel()).subscribe();
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                });

    }

    private TacoOrder checkFieldWhenUpdate(TacoOrder oldOrder, TacoOrder newOrder) {
        if (newOrder.getPlacedAt() != null)
            oldOrder.setPlacedAt(newOrder.getPlacedAt());
        if (!StringUtil.isNullOrEmpty(newOrder.getDeliveryName()))
            oldOrder.setDeliveryName(newOrder.getDeliveryName());
        if (!StringUtil.isNullOrEmpty(newOrder.getDeliveryStreet()))
            oldOrder.setDeliveryStreet(newOrder.getDeliveryStreet());
        if (!StringUtil.isNullOrEmpty(newOrder.getDeliveryCity()))
            oldOrder.setDeliveryCity(newOrder.getDeliveryCity());
        if (!StringUtil.isNullOrEmpty(newOrder.getDeliveryState()))
            oldOrder.setDeliveryState(newOrder.getDeliveryState());
        if (!StringUtil.isNullOrEmpty(newOrder.getDeliveryZip()))
            oldOrder.setDeliveryZip(newOrder.getDeliveryZip());
        if (!StringUtil.isNullOrEmpty(newOrder.getCcNumber()))
            oldOrder.setCcNumber(newOrder.getCcNumber());
        if (!StringUtil.isNullOrEmpty(newOrder.getCcExpiration()))
            oldOrder.setCcExpiration(newOrder.getCcExpiration());
        if (!StringUtil.isNullOrEmpty(newOrder.getCcCVV()))
            oldOrder.setCcCVV(newOrder.getCcCVV());
        if (!newOrder.getTacos().isEmpty())
            oldOrder.setTacos(newOrder.getTacos());
        return oldOrder;

    }

    @GetMapping("/{orderId}")
    public Mono<ResponseEntity<TacoOrder>> orderById(@PathVariable("orderId") String orderId) {
        log.info("ORDER: {}", orderId);
        return orderRepo
                .findById(orderId)
                .switchIfEmpty(Mono.just(TacoOrder.empty))
                .flatMap(order -> !Objects.equals(order, TacoOrder.empty) ? Mono.just(new ResponseEntity<>(order, HttpStatus.OK)) : Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));

    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TacoOrder> createOrder(@Valid @RequestBody TacoOrder order) {
        order = order.withPlacedAt(new Date());
        return orderRepo.save(order);
    }

    @DeleteMapping("/{orderId}")
    public Mono<ResponseEntity<Void>> deleteOrder(@PathVariable("orderId") String orderId) {
        return orderRepo
                .findById(orderId)
                .switchIfEmpty(Mono.just(TacoOrder.empty))
                .map(o -> {
                    if (Objects.equals(o, TacoOrder.empty))
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    orderRepo.deleteById(orderId).subscribeOn(Schedulers.parallel()).subscribe();
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                });
    }


//  @PatchMapping(path = "/{orderId}", consumes = "application/json")
//  public TacoOrder patchOrder(@PathVariable("orderId") Long orderId, @RequestBody TacoOrder patch) {
//    TacoOrder order = orderRepo.findById(orderId).get();
//    if (patch.getDeliveryName() != null)
//      order.setDeliveryName((patch.getDeliveryName()));
//    if (patch.getDeliveryCity() != null)
//      order.setDeliveryCity(patch.getDeliveryCity());
//    if (patch.getDeliveryState() != null)
//      order.setDeliveryState(patch.getDeliveryState());
//    if (patch.getDeliveryZip() != null)
//      order.setDeliveryZip(patch.getDeliveryZip());
//    if (patch.getCcNumber() != null)
//      order.setCcNumber(patch.getCcNumber());
//    if (patch.getCcExpiration() != null)
//      order.setCcExpiration(patch.getCcExpiration());
//    if (patch.getCcCVV() != null)
//      order.setCcCVV(patch.getCcCVV());
//    return orderRepo.save(order);
//  }
//
//  @DeleteMapping("/{orderId}")
//  @ResponseStatus(HttpStatus.NO_CONTENT)
//  public void deleteOrder(@PathVariable("orderId")Long id) {
//    try {
//      orderRepo.deleteById(id);
//    } catch (EmptyResultDataAccessException e) {
//
//    }
//
//  }
}
