package com.example.myspringproject.controller;

import com.example.myspringproject.config.Configuration;
import com.example.myspringproject.model.*;
import com.example.myspringproject.service.UserPageService;
import com.example.myspringproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Optional;

@Controller
public class    MainController {

    public static final Logger logger = Logger.getLogger(String.valueOf(MainController.class));
    @Autowired
    private Configuration configuration;
    @Autowired
    private UserService userService;


    @GetMapping( "/")
    public String mainPage(HttpServletResponse response, Model model) {
        Cookie cookie = new Cookie("locale", "eng");
        cookie.setPath("/");
        cookie.setMaxAge(86400);
        response.addCookie(cookie);

        model.addAttribute("locale", configuration.getLocale());
        return "main";
    }

    @PostMapping( "/")
    public String getMainPage(HttpServletResponse response, @RequestParam String locale, Model model) {
        configuration.setLocale(locale);
        model.addAttribute("locale", configuration.getLocale());
        Cookie cookie = new Cookie("locale", locale);
        cookie.setPath("/");
        cookie.setMaxAge(86400);
        response.addCookie(cookie);

        return "main";
    }

    String exception = "";

    @GetMapping("/signIn")
    public String signInPage(Model model) {
        model.addAttribute("locale", configuration.getLocale());
        model.addAttribute("exception", this.exception);
        this.exception = "";
        return "signIn";
    }

    @GetMapping("/forbidden")
    public String forbiddenPage(Model model) {
        model.addAttribute("locale", configuration.getLocale());
        return "forbidden";
    }

    @GetMapping("/logIn")
    public String logInPage(Model model) {
        model.addAttribute("locale", configuration.getLocale());
        model.addAttribute("exception", this.exception);
        this.exception = "";
        return "logIn";
    }

    @PostMapping("/signIn")
    public String getDataFromUserPage(HttpServletResponse response, @RequestParam String username, @RequestParam String password, Model model) {
        Optional<UserEntity> user = userService.findUser(username, password);
        if (user.isEmpty()) {
            logger.log(Level.WARNING, "No user with such login :: " + username);
            this.exception = "No user with such login";
            return "redirect:/signIn";
        }
        Cookie cookie = new Cookie("user-id", user.get().getId().toString());
        cookie.setPath("/");
        cookie.setMaxAge(86400);
        response.addCookie(cookie);
        return (user.get().getRole().name().equals("ADMIN") ? "redirect:/admin/main/" + user.get().getId() : "redirect:/user/main/" + user.get().getId());
    }

    @PostMapping("/logIn")
    public String getUserDataLogInPage(HttpServletResponse response, @RequestParam String username, @RequestParam String password, @RequestParam String password1, Model model) {
        if(password.equals(password1)) {
            UserEntity user = userService.saveUser(username, password).get();
            Cookie cookie = new Cookie("user-id", user.getId().toString());
            cookie.setPath("/");
            cookie.setMaxAge(86400);
            response.addCookie(cookie);
            return (userService.getNumberOfRows() == 1 ? "redirect:/admin/main/" + user.getId() : "redirect:/user/main/" + user.getId());
        } else {
            logger.log(Level.WARNING, "The password are different " + password + " && " + password1);
            this.exception = "The passwords are different. Please, try again";
            return "redirect:/logIn";
        }
    }

}
