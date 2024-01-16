package tacos.messaging;

import tacos.domain.TacoOrder;

public interface OrderMessagingService {
  void sendOrder(TacoOrder order);
}
