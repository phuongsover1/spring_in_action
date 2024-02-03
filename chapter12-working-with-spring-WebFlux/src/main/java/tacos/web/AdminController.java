package tacos.web;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.services.OrderAdminService;

@Controller
@RequestMapping("/admin")
@Data
public class AdminController {
  private final OrderAdminService adminService;

  @PostMapping("/deleteOrders")
  public String deleteAllOrders() {
    adminService.deleteAllOrders();
    return "redirect:/admin";
  }
}
