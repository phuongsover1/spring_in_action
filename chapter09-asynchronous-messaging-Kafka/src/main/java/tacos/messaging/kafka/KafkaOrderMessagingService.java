package tacos.messaging.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.ToStringSerializer;
import org.springframework.stereotype.Service;
import tacos.domain.TacoOrder;
import tacos.messaging.OrderMessagingService;

@Service
@RequiredArgsConstructor
public class KafkaOrderMessagingService implements OrderMessagingService {
  private final KafkaTemplate<String, TacoOrder> kafkaTemplate;
  @Override
  public void sendOrder(TacoOrder order) {

  }

  @Override
  public void sendOrder(TacoOrder order, String place) {
//    kafkaTemplate.sendDefault("tacocloud.orders.topic", order);
    kafkaTemplate.send("tacocloud.orders.topic", order);
  }
}
