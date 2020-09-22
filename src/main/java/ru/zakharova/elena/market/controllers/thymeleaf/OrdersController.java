package ru.zakharova.elena.market.controllers.thymeleaf;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.zakharova.elena.market.beans.Cart;
import ru.zakharova.elena.market.entities.Order;
import ru.zakharova.elena.market.entities.User;
import ru.zakharova.elena.market.services.OrdersService;
import ru.zakharova.elena.market.services.UsersService;

import java.security.Principal;

@Controller
@RequestMapping("/orders")
@AllArgsConstructor
public class OrdersController {
    private UsersService usersService;
    private OrdersService ordersService;
    private Cart cart;

    @GetMapping("/create")
    public String createOrder(Principal principal, Model model) {
        User user = usersService.findByPhone(principal.getName()).get();
        model.addAttribute("user", user);
        return "order_info";
    }

    @PostMapping("/confirm")
    public String confirmOrder(Principal principal, @RequestParam String address, @RequestParam String phone) {
        User user = usersService.findByPhone(principal.getName()).get();
        Order order = new Order(user, cart, phone, address);
        order = ordersService.save(order);
        return "order_results";
    }
}
