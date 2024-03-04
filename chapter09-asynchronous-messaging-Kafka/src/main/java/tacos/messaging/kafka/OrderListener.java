package tacos.messaging.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tacos.domain.TacoOrder;

@Slf4j
@Component
public class OrderListener {

  @KafkaListener(topics = "tacocloud.orders.topic")
  public void handle(TacoOrder order){
    log.info("New Order From Kafka: " + order);
  }
}
