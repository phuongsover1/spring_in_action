package tacos.messaging;

import jakarta.jms.Destination;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {
  // Define custom destination queue
  @Bean
  public Destination customQueue() {
    return new ActiveMQQueue("tacocloud.custom.queue");
  }
}

