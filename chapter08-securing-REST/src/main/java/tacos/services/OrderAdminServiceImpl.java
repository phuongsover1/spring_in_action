package tacos.services;

import lombok.Data;
import org.springframework.stereotype.Service;
import tacos.data.OrderRepository;

@Service
@Data
public class OrderAdminServiceImpl implements OrderAdminService {
  private final OrderRepository orderRepo;
  @Override
  public void deleteAllOrders() {
    orderRepo.deleteAll();
  }
}
