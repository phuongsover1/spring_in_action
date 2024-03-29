package tacos.messaging.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import tacos.domain.TacoOrder;

@Component
@RequiredArgsConstructor
public class RabbitOrderReceiver implements OrderReceiver {
  private final RabbitTemplate rabbit;
  private final MessageConverter converter;

  public TacoOrder receiveOrder(long timeout) {
    // Receive()
//    Message message = rabbit.receive("order.kitchens", timeout);
//    return message != null ? (TacoOrder) converter.fromMessage(message) : null;

    // ReceiveAndConver()
    return rabbit.receiveAndConvert("order.kitchens",timeout, new ParameterizedTypeReference<TacoOrder>() {
    });

  }
}
