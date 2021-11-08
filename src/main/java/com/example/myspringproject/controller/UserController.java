package com.example.myspringproject.controller;

import com.example.myspringproject.config.Configuration;
import com.example.myspringproject.model.ActivityEntity;
import com.example.myspringproject.model.Role;
import com.example.myspringproject.model.Type;
import com.example.myspringproject.repository.UserRepository;
import com.example.myspringproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserActivityService userActivityService;
    @Autowired
    private ActivityPageService activityPageService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private Configuration configuration;

    @GetMapping( "/user/main/{id}")
    public String mainPage(@CookieValue(name="user-id", defaultValue="") String userId, @CookieValue(name = "locale") String locale, @PathVariable(value = "id") Long id, Model model) {
        configuration.setLocale(locale);
        model.addAttribute("locale", configuration.getLocale());
        model.addAttribute("user", userService.findUserById(id));
        return "users/userMain";
    }

    @GetMapping("/user/listOfActivity/{id}")
    public String listOfActivityPage(@CookieValue(name = "user-id") String userId, @PathVariable(value = "id") Long id, @RequestParam(defaultValue = "") String dir, Model model) {
        model.addAttribute("locale", configuration.getLocale());
        model.addAttribute("activities", activityPageService.getActivityPaging(dir,"id"));
        model.addAttribute("user", userService.findUserById(id));
        return "users/listOfActivity";
    }

    @GetMapping("/user/listOfActivity/{id}/sortByName")
    public String listOfActivityPageSortByName(@CookieValue(name = "user-id") String userId, @PathVariable(value = "id") Long id, @RequestParam(defaultValue = "") String dir, Model model) {
        model.addAttribute("locale", configuration.getLocale());
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("activities", activityPageService.getActivityPaging(dir,"name"));
        return "users/listOfActivityByName";
    }

    @GetMapping("/user/listOfActivity/{id}/sortByCategory")
    public String listOfActivityPageSortSortByCategory(@CookieValue(name = "user-id") String userId, @PathVariable(value = "id") Long id, @RequestParam(defaultValue = "") String dir, Model model) {
        model.addAttribute("locale", configuration.getLocale());
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("activities", activityPageService.getActivityPaging(dir,"category"));
        return "users/listOfActivityByCategory";
    }

    @GetMapping("/user/listOfActivity/{userId}/addAnActivity/{activityId}")
    public String listOfActivityAddPage(HttpServletResponse response, @CookieValue(name = "user-id") String id, @PathVariable(name = "userId") Long userId, @PathVariable(name = "activityId") Long activityId, Model model) {
        model.addAttribute("locale", configuration.getLocale());
        model.addAttribute("user", userService.findUserById(userId));
        model.addAttribute("activity", activityService.getActivity(activityId));
        return "users/addAnActivity";
    }

    @PostMapping("user/listOfActivity/{userId}/addAnActivity/{activityId}")
    public String getListOfActivityAddPage(@PathVariable(name = "userId") Long userId, @PathVariable(name = "activityId") Long activityId, @RequestParam String hours) {
        requestService.addRequest(userId, activityId, hours, Type.ADD);
        return "redirect:/user/listOfActivity/" + userId;
    }

    @GetMapping("/user/myActivities/{id}")
    public String listOfMyActivitiesPage(HttpServletResponse response, @CookieValue(name = "user-id") String userId, @PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("locale", configuration.getLocale());
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("activities", userService.findUserById(id).getActivities());
        model.addAttribute("hours", userActivityService.findByUserId(id));
        return "users/listMyActivity";
    }

    @GetMapping("/user/myActivities/{userId}/editAnActivity/{activityId}")
    public String myActivitiesEditPage(HttpServletResponse response, @CookieValue(name = "user-id") String user, @PathVariable(value = "userId") Long userId, @PathVariable(value = "activityId") Long activityId, Model model) {
        model.addAttribute("locale", configuration.getLocale());
        model.addAttribute("user", userService.findUserById(userId));
        model.addAttribute("activity", activityService.getActivity(activityId));
        model.addAttribute("userActivity", userActivityService.findByUserIdAndActivityId(userId, activityId));
        return "users/editAnActivity";
    }

    @PostMapping("/user/myActivities/{userId}/editAnActivity/{activityId}")
    public String getMyActivitiesEditPage(@PathVariable(name = "userId") Long userId, @PathVariable(name = "activityId") Long activityId, @RequestParam String hours) {
        requestService.addRequest(userId, activityId, hours, Type.EDIT);
        return "redirect:/user/myActivities/" + userId;
    }

    @GetMapping("/user/myActivities/{userId}/delete/{activityId}")
    public String myActivitiesDeletePage(HttpServletResponse response, @CookieValue(name = "user-id") String user, @PathVariable(name = "userId") Long userId, @PathVariable(name = "activityId") Long activityId) {
        requestService.addRequest(userId, activityId, Type.DELETE);
        return "redirect:/user/myActivities/" + userId;
    }
}
