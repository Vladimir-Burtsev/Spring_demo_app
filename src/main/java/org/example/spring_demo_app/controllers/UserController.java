package org.example.spring_demo_app.controllers;

import org.example.spring_demo_app.models.User;
import org.example.spring_demo_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String findById(@RequestParam("id") long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "user/user-bootstrap";
    }
}
