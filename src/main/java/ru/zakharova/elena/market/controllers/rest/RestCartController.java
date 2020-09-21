package ru.zakharova.elena.market.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.zakharova.elena.market.beans.Cart;
import ru.zakharova.elena.market.entities.Product;
import ru.zakharova.elena.market.entities.dtos.OrderItemDto;
import ru.zakharova.elena.market.exceptions.ProductNotFoundException;
import ru.zakharova.elena.market.services.OrderItemsService;
import ru.zakharova.elena.market.services.ProductsService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/cart")
@AllArgsConstructor
public class RestCartController {
    private OrderItemsService orderItemsService;
    private ProductsService productsService;
    private Cart cart;

    @GetMapping(produces = "application/json")
    public List<OrderItemDto> getAllOrderItemDto() {
        return orderItemsService.mapEntityListToDtoList(cart.getItems());
    }

    @GetMapping("/{productId}")
    public void addProductInCartById(@PathVariable Long productId) {
        Product product = productsService.findById(productId).orElseThrow(() -> new ProductNotFoundException("Unable to add product (id = " + productId + " ) to cart. Product is not found"));
        cart.add(product);
    }

    @DeleteMapping("/{productId}")
    public void deleteProductFromCartByProductId(@PathVariable Long productId) {
        cart.removeByProductId(productId);
    }

    @GetMapping("/decrement/{productId}")
    public void decrementProductInCartByProductId(@PathVariable Long productId) {
        Product product = productsService.findById(productId).get();
        cart.decrement(product);
    }

    @GetMapping(value = "/price", produces = "application/json")
    public BigDecimal getPriceOfCart() {
        return cart.getPrice();
    }


}