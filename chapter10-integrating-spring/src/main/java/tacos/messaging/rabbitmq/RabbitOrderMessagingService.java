package tacos.messaging.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Service;
import tacos.domain.TacoOrder;
import tacos.messaging.OrderMessagingService;

@Service
@RequiredArgsConstructor
public class RabbitOrderMessagingService implements OrderMessagingService {
  private final RabbitTemplate rabbit;
  @Override
  public void sendOrder(TacoOrder order) {
    MessageConverter converter = rabbit.getMessageConverter();
    MessageProperties properties = new MessageProperties();
    Message message = converter.toMessage(order, properties);
    rabbit.send("order.kitchens", message);
  }

  @Override
  public void sendOrder(TacoOrder order, String place) {
    rabbit.convertAndSend("order.kitchens", order, new MessagePostProcessor() {
      @Override
      public Message postProcessMessage(Message message) throws AmqpException {
        MessageProperties props = message.getMessageProperties();
        props.setHeader("X_ORDER_SOURCE", place);
        return message;
      }
    });
  }
}
