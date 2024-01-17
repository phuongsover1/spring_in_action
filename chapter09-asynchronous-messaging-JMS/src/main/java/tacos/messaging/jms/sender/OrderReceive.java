package tacos.messaging.jms.sender;

import jakarta.jms.JMSException;
import tacos.domain.TacoOrder;

public interface OrderReceive {
  TacoOrder receiveOrder() throws JMSException;

  TacoOrder receiveAndConvertOrder() throws  JMSException;
}
