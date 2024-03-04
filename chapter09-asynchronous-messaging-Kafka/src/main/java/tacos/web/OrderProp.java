package tacos.web;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Component
@ConfigurationProperties(prefix = "tacos.orders")
@Validated
public class OrderProp {
  @Min(value = 5, message = "Must be between 5 and 25")
  @Max(value = 25, message = "Must be between 5 and 25")
  private int pageSize = 25;
}
