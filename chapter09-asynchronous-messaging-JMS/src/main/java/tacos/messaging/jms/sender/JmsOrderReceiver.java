package tacos.messaging.jms.sender;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;
import tacos.domain.TacoOrder;

@Component
@RequiredArgsConstructor
public class JmsOrderReceiver implements OrderReceive {
  private final JmsTemplate jms;
  private final MessageConverter converter;


  @Override
  public TacoOrder receiveOrder() throws JMSException {
    Message message = jms.receive("tacocloud.order.queue");
    return (TacoOrder) converter.fromMessage(message);
  }

  @Override
  public TacoOrder receiveAndConvertOrder() throws JMSException {
    return (TacoOrder) jms.receiveAndConvert("tacocloud.order.queue");
  }
}
