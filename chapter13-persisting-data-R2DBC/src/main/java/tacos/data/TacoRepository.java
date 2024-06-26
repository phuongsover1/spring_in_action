package tacos.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import tacos.domain.Taco;

public interface TacoRepository extends ReactiveCrudRepository<Taco, Long> {
}
