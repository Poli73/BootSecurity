package com.boot.bootSecurity.controllers;


import com.boot.bootSecurity.model.User;

import com.boot.bootSecurity.service.RoleService;
import com.boot.bootSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

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

    @GetMapping
    public String adminHome() {
        return "redirect:/admin/users/list";
    }

    @GetMapping("users/list")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin-list-users";
    }


    @GetMapping("users/create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin-create-user";
    }


    @PostMapping("users/create")
    public String createUser(@ModelAttribute User user, @RequestParam List<Long> roleIds) {
        userService.createUser(user, roleIds);
        return "redirect:/admin/users/list";
    }


    @GetMapping("users/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin-edit-user";
    }


    @PostMapping("users/edit/{id}")
    public String updateUser(@PathVariable Long id,
                             @ModelAttribute User user,
                             @RequestParam List<Long> roleIds) {
        userService.updateUser(id, user, roleIds);
        return "redirect:/admin/users/list";
    }


    @GetMapping("users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users/list";
    }
}