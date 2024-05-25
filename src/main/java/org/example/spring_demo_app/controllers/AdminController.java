package org.example.spring_demo_app.controllers;

import jakarta.validation.Valid;
import org.example.spring_demo_app.models.User;
import org.example.spring_demo_app.services.RoleService;
import org.example.spring_demo_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping()
    public String findAll(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "AllUsers";
    }
    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAllRoles());
        return "NewUser";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "NewUser";
        }

        userService.saveUser(user);
        return "redirect:/admin";
    }
}
