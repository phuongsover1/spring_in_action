package tacos.messaging;

import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Service;
import tacos.domain.TacoOrder;

@Service
@RequiredArgsConstructor
public class JmsOrderMessagingService implements OrderMessagingService {
  private final JmsTemplate jms;

  @Override
  public void sendOrder(TacoOrder order) {
    jms.send(session -> session.createObjectMessage(order));
  }

  @Override
  public void sendOrder(Destination destination, TacoOrder order) {
    jms.send(destination, session -> session.createObjectMessage(order));
  }

  @Override
  public void convertAndSend(TacoOrder order) {
    jms.convertAndSend(order);
  }

  @Override
  public void convertAndSend(TacoOrder order, String place) {
    jms.convertAndSend(order, new MessagePostProcessor() {
      @Override
      public Message postProcessMessage(Message message) throws JMSException {
        message.setStringProperty("X_ORDER_SOURCE", place);
        return message;
      }
    });
  }

  @Override
  public void convertAndSend(Destination destination, TacoOrder order, String place) {
    jms.convertAndSend(destination, order, new MessagePostProcessor() {
      @Override
      public Message postProcessMessage(Message message) throws JMSException {
        message.setStringProperty("X_ORDER_SOURCE", place);
        return message;
      }
    });
  }


}
