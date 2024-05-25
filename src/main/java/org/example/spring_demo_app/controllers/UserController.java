package org.example.spring_demo_app.controllers;

import jakarta.validation.Valid;
import org.example.spring_demo_app.models.User;
import org.example.spring_demo_app.services.RoleService;
import org.example.spring_demo_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String findById(@RequestParam("id") int id, Model model) {
        try {
            User user = userService.findUserById(id);
            model.addAttribute("user", user);
            return "UserDetails";
        } catch (NoSuchElementException e) {
            return "NotFound";
        }
    }

    @GetMapping("/edit")
    public String editForm(Model model, @RequestParam(value = "id") long id) {
        model.addAttribute("roles", roleService.findAllRoles());
        model.addAttribute("user", userService.findUserById(id));
        return "Edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult, @RequestParam("id") long id) {
        if (bindingResult.hasErrors()) {
            return "Edit";
        }
        userService.updateUser(user);
        return "redirect:/user";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUserById(id);
        return "redirect:/user";
    }
}