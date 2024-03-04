package tacos.messaging.rabbitmq;

import tacos.domain.TacoOrder;

public interface OrderReceiver {
  TacoOrder receiveOrder(long timeout);
}
