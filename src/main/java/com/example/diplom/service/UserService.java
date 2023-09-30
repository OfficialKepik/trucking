package com.example.diplom.service;

import com.example.diplom.repository.UserRepo;
import com.example.diplom.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User findById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    public void save(User user) {
        userRepo.save(user);
    }

    public void delete(Long id) {
        userRepo.delete(userRepo.findById(id).get());
    }

    public User findByEmail(String email) {
        return userRepo.findByEmailUser(email).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }

    public User findByPhoneNumber(String phoneNumber) {
        return userRepo.findByPhoneUser(phoneNumber).orElse(null);
    }

    public void update(Long id, User user) {
        user.setId(id);
        userRepo.save(user);
    }
}