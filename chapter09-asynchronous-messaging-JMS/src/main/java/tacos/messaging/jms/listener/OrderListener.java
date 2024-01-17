package tacos.messaging.jms.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import tacos.domain.TacoOrder;

@Slf4j
@Profile("jms-listener")
@Component
public class OrderListener {
  @JmsListener(destination = "tacocloud.order.queue")
   public void receiveOrder(TacoOrder order)  {
     log.info("New Order: " + order);
   }
}
