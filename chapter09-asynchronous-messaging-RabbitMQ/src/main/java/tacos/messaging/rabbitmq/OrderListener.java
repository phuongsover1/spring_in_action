package tacos.messaging.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tacos.domain.TacoOrder;

@Slf4j
@Component
public class OrderListener {

  @RabbitListener(queues = "order.kitchens")
  public void autoReceiveAndLogOrder(TacoOrder order) {
    log.info("Listening Order: " + order);
  }
}
