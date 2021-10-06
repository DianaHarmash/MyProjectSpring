package com.example.myprojectspring.controllers;

import com.example.myprojectspring.model.UserEntity;
import com.example.myprojectspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/userMyMain/{id}")
    public String userMain(@PathVariable(value = "id") long id, Model model) {
        Optional<UserEntity> user = userRepository.findById(id);
        model.addAttribute("user", user.get());
        return "users/userMyMain";
    }
}
