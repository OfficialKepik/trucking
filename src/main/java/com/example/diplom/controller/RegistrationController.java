package com.example.diplom.controller;

import com.example.diplom.model.Email;
import com.example.diplom.service.EmailService;
import com.example.diplom.service.RoleService;
import com.example.diplom.service.UserService;
import com.example.diplom.user.UserValidator;
import com.example.diplom.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@SessionAttributes("order")
public class RegistrationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final EmailService emailService;
    private final JavaMailSender sender;

    private final UserValidator userValidator;

    @Autowired
    public RegistrationController(UserService userService, PasswordEncoder passwordEncoder, RoleService roleService, EmailService emailService, JavaMailSender sender, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.sender = sender;
        this.userValidator = userValidator;
    }

    @GetMapping("/registration")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String processRegistration(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

        userValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors())
            return "registration";

        String oldPass = user.getPassword();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleService.findByName("ROLE_USER"));
        userService.save(user);
        sendRegistrationEmail(user, oldPass);
        return "redirect:/login";
    }

    public void sendRegistrationEmail(User user, String oldPass) {
        Email email = new Email();
        email.setFrom("officialkepik@gmail.com");
        email.setTo(user.getEmailUser());
        email.setSubject("Успешная регистрация на Trucking.com");
        email.setText(String.format(
                "Здравствуйте " + user.getFio() + " ,\n\n" +
                        "Спасибо за регистрацию на Trucking.com. Мы рады приветствовать вас в нашем сообществе!\n\n" +
                        "Ваши регистрационные данные:\n" +
                        "Логин: " + user.getUsername() + "\n" +
                        "Пароль: " + oldPass + "\n\n" +
                        "Пожалуйста, сохраните эту информацию в надежном месте. Вы можете использовать ее для входа в вашу учетную запись нашего приложения.\n\n" +
                        "Если это были не вы, кто зарегистрировался, пожалуйста, немедленно свяжитесь с нашей поддержкой.\n\n" +
                        "С наилучшими пожеланиями,\n" +
                        "Команда Trucking for You"));
        emailService.sendTextEmail(email);
    }
}
