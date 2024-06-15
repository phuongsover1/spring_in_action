package tacos.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import tacos.domain.TacoOrder;

public interface OrderRepository extends ReactiveCrudRepository<TacoOrder, String> {
}
