package tacos.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import tacos.domain.TacoOrder;

public interface OrderRepository extends ReactiveCrudRepository<TacoOrder, Long> {

}
