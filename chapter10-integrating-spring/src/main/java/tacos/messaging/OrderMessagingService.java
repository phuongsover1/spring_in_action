package tacos.messaging;

import tacos.domain.Taco;
import tacos.domain.TacoOrder;

public interface OrderMessagingService {
  void sendOrder(TacoOrder order);

  void sendOrder(TacoOrder order, String place);
}
