package org.example.spring_demo_app.controllers;

import jakarta.validation.Valid;
import org.example.spring_demo_app.models.User;
import org.example.spring_demo_app.services.RoleService;
import org.example.spring_demo_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    public String findAll(Model model, Principal principal) {
        User currentUser = userService.findByUsername(principal.getName()).get();
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAllRoles());
        model.addAttribute("userCurrent", currentUser);
        return "admin/show-all-users";
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
    @GetMapping("/edit")
    public String editForm(Model model, @RequestParam(value = "id") long id) {
        model.addAttribute("roles", roleService.findAllRoles());
        model.addAttribute("user", userService.findUserById(id));
        return "Edit";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult, @RequestParam("id") long id) {
        if (bindingResult.hasErrors()) {
            return "Edit";
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
