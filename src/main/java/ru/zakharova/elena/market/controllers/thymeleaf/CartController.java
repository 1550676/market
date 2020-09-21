package ru.zakharova.elena.market.controllers.thymeleaf;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.zakharova.elena.market.beans.Cart;
import ru.zakharova.elena.market.services.ProductsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {
    private ProductsService productsService;
    private Cart cart;

    @GetMapping
    public String showCartPage() {
        return "cart";
    }

    @GetMapping("/add/{productId}")
    public void addProductToCartById(@PathVariable Long productId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        cart.add(productsService.findById(productId).get());
        response.sendRedirect(request.getHeader("referer"));
    }

    @GetMapping("/decrement/{productId}")
    public void decrementProductToCartById(@PathVariable Long productId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        cart.decrement(productsService.findById(productId).get());
        response.sendRedirect(request.getHeader("referer"));
    }

    @GetMapping("/remove/{productId}")
    public void removeProductFromCartById(@PathVariable Long productId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        cart.removeByProductId(productId);
        response.sendRedirect(request.getHeader("referer"));
    }
}
