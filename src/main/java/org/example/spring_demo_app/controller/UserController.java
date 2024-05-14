package org.example.spring_demo_app.controller;

import jakarta.validation.Valid;
import org.example.spring_demo_app.model.User;
import org.example.spring_demo_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String findAll(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "AllUsers";
    }

    @GetMapping("{id}")
    public String findById(@PathVariable int id, Model model) {
        try {
            User user = userService.findUserById(id);
            model.addAttribute("user", user);
            return "UserDetails";
        }catch (NoSuchElementException e) {
            return "NotFound";
        }
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "NewUser";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "NewUser";
        }
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam(value = "id") long id) {
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
        return "redirect:/users";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }
}
