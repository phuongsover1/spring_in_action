package tacos.data;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import tacos.domain.Taco;

import java.util.List;

public interface TacoRepository extends CrudRepository<Taco, Long> {
  Iterable<Taco> findAllBy(PageRequest page);
}
