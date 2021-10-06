package com.example.myprojectspring.controllers;

import com.example.myprojectspring.model.AdminEntity;
import com.example.myprojectspring.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/adminMyMain/{id}")
    public String userMain(@PathVariable(value = "id") long id, Model model) {
        Optional<AdminEntity> myAdmin = adminRepository.findById(id);
        model.addAttribute("admin", myAdmin.get());
        return "admin/adminMyMain";
    }
}
