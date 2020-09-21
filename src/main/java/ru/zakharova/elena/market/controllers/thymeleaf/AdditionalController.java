package ru.zakharova.elena.market.controllers.thymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.zakharova.elena.market.entities.User;
import ru.zakharova.elena.market.services.UsersService;

import java.security.Principal;

@Controller
public class AdditionalController {

    private UsersService usersService;

    @Autowired
    public AdditionalController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login_page";
    }

    @GetMapping("/profile")
    @ResponseBody
    public String profilePage(Principal principal) {
        User user = (User) usersService.getUserByUsername(principal.getName());
        return user.toString();
    }
}