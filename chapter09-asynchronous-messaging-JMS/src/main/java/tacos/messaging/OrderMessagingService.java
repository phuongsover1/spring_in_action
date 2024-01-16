package tacos.messaging;

import jakarta.jms.Destination;
import tacos.domain.TacoOrder;

public interface OrderMessagingService {
  void sendOrder(TacoOrder order);
  void sendOrder(Destination destination, TacoOrder order);
}
