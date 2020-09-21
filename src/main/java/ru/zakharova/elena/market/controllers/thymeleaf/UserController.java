package ru.zakharova.elena.market.controllers.thymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.zakharova.elena.market.entities.User;
import ru.zakharova.elena.market.services.UsersService;

import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {
    private UsersService usersService;

    @Autowired
    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public String showAll(Model model, @RequestParam Map<String, String> requestParams)  {
        Integer pageNumber = Integer.parseInt(requestParams.getOrDefault("p", "1"));
        Page<User> users = usersService.findAll(pageNumber);
        model.addAttribute("users", users);
        return "all_users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", usersService.findById(id));
        return "edit_user_form";
    }

    @PostMapping("/edit")
    public String modifyProduct(@ModelAttribute User user) {
        usersService.saveOrUpdate(user);
        return "redirect:/users";
    }

}