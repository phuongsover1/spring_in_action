package tacos.web;

import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import tacos.data.OrderRepository;
import tacos.domain.TacoOrder;

@Slf4j
@Controller
@Data
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    private final OrderRepository orderRepo;
    private final OrderProp orderProp;


    @GetMapping("/current")
    public String orderForm(Model model) {
        model.addAttribute("order", new TacoOrder());
        return "orderForm";
    }

//    @PostMapping
//    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus) {
//        log.info("Order submittted: " + order);
//
//        if (errors.hasErrors()) {
//            return "orderForm";
//        }
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
//        order.setUser(user);
//        orderRepo.save(order);
//        sessionStatus.setComplete();
//        return "redirect:/";
//    }

//    @GetMapping
//    public String ordersForUser(@AuthenticationPrincipal User user, Model model) {
//        Pageable pageable = PageRequest.of(0, orderProp.getPageSize());
//        model.addAttribute("orders", orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));
//        return "orderList";
//    }
}
