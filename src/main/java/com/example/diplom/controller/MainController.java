package com.example.diplom.controller;

import com.example.diplom.model.Order;
import com.example.diplom.model.User;
import com.example.diplom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("order")
public class MainController {

    private final UserService userService;

    @ModelAttribute
    public Order createOrder() {
        return new Order();
    }

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if ((authentication.getPrincipal() instanceof String)) {
            model.addAttribute("not_auth", true);
        } else {
            model.addAttribute("auth", true);
        }
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

}