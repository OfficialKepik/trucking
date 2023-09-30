package com.example.diplom.user;

import com.example.diplom.model.User;
import com.example.diplom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userService.findByEmail(user.getEmailUser()) != null)
            errors.rejectValue("username", "", "Этот Логин уже используется другим пользователем");
        else if (userService.findByUsername(user.getUsername()) != null)
            errors.rejectValue("emailUser", "", "Этот Email уже используется другим пользователем");
        else if (userService.findByPhoneNumber(user.getPhoneUser()) != null)
            errors.rejectValue("phoneUser", "", "Этот Телефон уже используется другим пользователем");
    }
}
