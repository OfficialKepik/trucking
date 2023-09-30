package com.example.diplom.service;

import com.example.diplom.model.Order;
import com.example.diplom.repository.OrderRepo;
import com.example.diplom.repository.UserRepo;
import com.example.diplom.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo, UserRepo userRepo) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public void save(Order order) {
        orderRepo.save(order);
    }

    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    public User getUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    @Transactional
    public void updateUserId(Long oldUserId, Long newUserId) {
        orderRepo.updateUserId(oldUserId, newUserId);
    }

    public Order findById(Long id) {
        try {
            return orderRepo.findById(id).orElse(null);
        } catch (Exception e) {
            throw new NoSuchElementException("we dont have order with this id");
        }
    }
}