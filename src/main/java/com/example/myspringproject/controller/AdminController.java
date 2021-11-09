package com.example.myspringproject.controller;

import com.example.myspringproject.config.Configuration;
import com.example.myspringproject.model.UserEntity;
import com.example.myspringproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserPageService userPageService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private ActivityPageService activityPageService;
    @Autowired
    private Configuration configuration;

    @GetMapping( "/admin/main/{id}")
    public String mainPage(@CookieValue(name = "locale") String locale, @PathVariable(value = "id") Long id, Model model) {configuration.setLocale(locale);
        model.addAttribute("locale", configuration.getLocale());
        model.addAttribute("user", userService.findUserById(id));
        return "admins/adminMain";
    }

    @GetMapping("/admin/listOfActivity/{id}")
    public String listOfActivityPage(@PathVariable(value = "id") Long id, @RequestParam(defaultValue = "") String dir, Model model) {
        model.addAttribute("activities", activityPageService.getActivityPaging(dir,"id"));
        model.addAttribute("locale", configuration.getLocale());
        model.addAttribute("user", userService.findUserById(id));
        return "admins/listOfActivities";
    }

    @GetMapping("admin/listOfActivity/{id}/sortByName")
    public String listOfActivityPageSortByName(@PathVariable(value = "id") Long id, @RequestParam(defaultValue = "") String dir, Model model) {
        model.addAttribute("activities", activityPageService.getActivityPaging(dir,"name"));
        model.addAttribute("locale", configuration.getLocale());
        model.addAttribute("user", userService.findUserById(id));
        return "admins/listOfActivitiesByName";
    }

    @GetMapping("admin/listOfActivity/{id}/sortByCategory")
    public String listOfActivityPageSortByCategory(@PathVariable(value = "id") Long id, @RequestParam(defaultValue = "") String dir, Model model) {
        model.addAttribute("locale", configuration.getLocale());
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("activities", activityPageService.getActivityPaging(dir,"category"));
        return "admins/listOfActivitiesByCategory";
    }

    @GetMapping("admin/listOfActivity/{id}/sortByUsers")
    public String listOfActivityPageSortByUsers(@PathVariable(value = "id") Long id, @RequestParam(defaultValue = "") String dir, Model model) {
        model.addAttribute("locale", configuration.getLocale());
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("activities", activityPageService.getActivityPaging(dir,"users"));
        return "admins/listOfActivitiesByUsers";
    }


    @GetMapping("/admin/listOfActivity/{id}/addAnActivity")
    public String listOfActivityAdd(/*HttpServletResponse response,*/ @PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("locale", configuration.getLocale());
        model.addAttribute("user", userService.findUserById(id));
        return "admins/addAnActivity";
    }

    @PostMapping("/admin/listOfActivity/{id}/addAnActivity")
    public String getListOfActivityAdd(@RequestParam String name, @RequestParam String category, @PathVariable(name = "id") Long id) {
        activityService.saveActivity(name, category);
        return "redirect:/admin/listOfActivity/" + id;
    }

    @GetMapping("/admin/listOfActivity/{idUser}/delete/{idActivity}")
    public String listOfActivityDelete(/*HttpServletResponse response,*/ @PathVariable(name = "idUser") Long idUser, @PathVariable(name = "idActivity") Long idActivity) {
        activityService.deleteActivity(idActivity);
        return "redirect:/admin/listOfActivity/" + idUser ;
    }

    @GetMapping("/admin/listOfActivity/{idUser}/edit/{idActivity}")
    public String listOfActivityEdit(/*HttpServletResponse response,*/ @PathVariable(name = "idUser") Long idUser, @PathVariable(name = "idActivity") Long idActivity, Model model) {
        model.addAttribute("locale", configuration.getLocale());
        model.addAttribute("user", userService.findUserById(idUser));
        model.addAttribute("activity", activityService.getActivity(idActivity));
        return "admins/editAnActivity";
    }

    @PostMapping("/admin/listOfActivity/{idUser}/edit/{idActivity}")
    public String getListOfActivityEdit(@PathVariable(name = "idUser") Long idUser, @PathVariable(name = "idActivity") Long idActivity, @RequestParam String name, @RequestParam String category) {
        activityService.updateActivity(idActivity, name, category);
        return "redirect:/admin/listOfActivity/" + idUser;
    }

    @GetMapping("admin/listOfRequest/{userId}")
    public String listOfRequires(/*HttpServletResponse response,*/ @PathVariable(name = "userId") Long id, Model model) {
        model.addAttribute("locale", configuration.getLocale());
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("requests", requestService.getAllRequests());
        return "admins/listOfRequests";
    }

    @GetMapping("admin/listOfRequest/{userId}/request/{requestId}")
    public String listOfRequestsRequest(/*HttpServletResponse response,*/ @PathVariable(name = "userId") Long userId, @PathVariable(name = "requestId") Long requestId, Model model) {
        requestService.allowRequest(requestId);
        return "redirect:/admin/listOfRequest/" + userId;
    }
}
