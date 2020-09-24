package ru.zakharova.elena.market.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zakharova.elena.market.beans.Cart;
import ru.zakharova.elena.market.entities.Order;
import ru.zakharova.elena.market.entities.User;
import ru.zakharova.elena.market.exceptions.MarketError;
import ru.zakharova.elena.market.services.OrdersService;
import ru.zakharova.elena.market.services.UsersService;

import java.security.Principal;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/order")
@AllArgsConstructor
public class RestOrderController {
    private OrdersService ordersService;
    private UsersService usersService;
    private Cart cart;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createOrder(Principal principal, @RequestBody String address) {
        try {
            User user = usersService.getUserByUsername(principal.getName());
            Order order = new Order(user, cart, principal.getName(), address);
            ordersService.save(order);
            return new ResponseEntity<>(new MarketError(HttpStatus.OK.value(), "Ваш заказ успешно сформирован!"), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(new MarketError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}