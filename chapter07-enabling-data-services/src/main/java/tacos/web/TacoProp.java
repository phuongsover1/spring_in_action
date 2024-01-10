package tacos.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "tacos")
public class TacoProp {
  private int pageSize = 15;
}
