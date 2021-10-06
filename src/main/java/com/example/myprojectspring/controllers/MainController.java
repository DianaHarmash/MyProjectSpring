package com.example.myprojectspring.controllers;

import com.example.myprojectspring.model.UserEntity;
import com.example.myprojectspring.model.AdminEntity;
import com.example.myprojectspring.repository.AdminRepository;
import com.example.myprojectspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AdminRepository adminRepository;

    // main page
    @GetMapping("/userMain")
    public String mainPage() {
        return "userMain";
    }

    // choose you are a user or admin
    @GetMapping("/userAdminSignIn")
    public String chooserUserAdminPage() {
        return "userAdmin";
    }

    // user sign in
    @GetMapping("/userSignIn")
    public String userSignInPage() {
        return "users/UserSignIn";
    }

    // entity get user
    @PostMapping("/userSignIn")
    public String getDataFromUserPage(@RequestParam String login, @RequestParam String password) {
        Iterable<UserEntity> myUsers = userRepository.findAll();
        final UserEntity[] user = {null};
        myUsers.forEach(myUser -> {
            if (myUser.getLogin().equals(login)) {
                if (myUser.getPassword().equals(password)) {
                    user[0] = myUser;
                }
            }
        });
        if (user[0] == null) {
            // TODO: exception there is no user with this login
        }
        return "redirect:/userMyMain/" + user[0].getId();
    }

    // choose you are a user or admin
    @GetMapping("/userAdminLogIn")
    public String userAdminPage() {
        return "userAdminLogIn";
    }

    // user log in
    @GetMapping("/userLogIn")
    public String userLogInPage() {
        return "users/userLogIn";
    }

    // entity save a new user
    @PostMapping("/userLogIn")
    public String getUserDataLogInPage(@RequestParam String login, @RequestParam String password, @RequestParam String password1) {
        if(password.equals(password1)) {
            Iterable<UserEntity> users = userRepository.findAll();
            users.forEach(user -> {
                if (user.getLogin().equals(login)) {
                    // TODO: exception user with login is in site
                }
            });
            UserEntity user = new UserEntity(login, password);
            userRepository.save(user);
            return "redirect:/userMyMain/" + user.getId();
        }
        // TODO: exception passwords are different
        return "";
    }

    @GetMapping("/adminSignIn")
    public String adminSignInPage() {
        return "admin/adminSignIn";
    }

    @PostMapping("/adminSignIn")
    public String getAdminDataSignInPage(@RequestParam String login, @RequestParam String password) {
        Iterable<AdminEntity> myAdmins = adminRepository.findAll();
        final AdminEntity[] admin = {null};
        myAdmins.forEach(myAdmin -> {
            if (myAdmin.getLogin().equals(login)) {
                if (myAdmin.getPassword().equals(password)) {
                    admin[0] = myAdmin;
                }
            }
        });
        if (admin[0] == null) {
            // TODO: exception there is no user with this login
        }
        return "redirect:/adminMyMain/" + admin[0].getId();
    }

    @GetMapping("/adminLogIn")
    public String adminLogInPage() {
        return "admin/adminLogIn";
    }

    @PostMapping("/adminLogIn")
    public String getAdminDataLogInPage(@RequestParam String login, @RequestParam String password, @RequestParam String password1) {
        if(password.equals(password1)) {
            Iterable<AdminEntity> admins = adminRepository.findAll();
            admins.forEach(admin -> {
                if (admin.getLogin().equals(login)) {
                    // TODO: exception user with login is in site
                }
            });
            AdminEntity admin = new AdminEntity(login, password);
            adminRepository.save(admin);
            return "redirect:/adminMyMain/" + admin.getId();
        }
        // TODO: exception passwords are different
        return "";
    }
}
