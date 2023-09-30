package com.example.diplom.service;

import com.example.diplom.repository.RoleRepo;
import com.example.diplom.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class RoleService {
    private final RoleRepo roleRepo;
    @Autowired
    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }
    @Transactional
    public Role findByName(String name){
        return roleRepo.findRoleByName(name).orElse(null);
    }

    public List<Role> findAll(){
        return roleRepo.findAll();
    }

}
