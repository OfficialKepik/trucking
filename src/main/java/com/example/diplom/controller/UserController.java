package com.example.diplom.controller;

import com.example.diplom.service.RoleService;
import com.example.diplom.service.UserService;
import com.example.diplom.model.User;
import com.example.diplom.user.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("order")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping("/user/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        user.setRole(roleService.findByName("ROLE_USER"));
        userService.update(id, user);
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        User user = userDetail.getUser();
        model.addAttribute("user", user);

        if (user.getRole().getId() == 1) {
            model.addAttribute("admin", true);
        }

        return "profile";
    }
}
