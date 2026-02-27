package com.boot.bootSecurity.controllers;

import com.boot.bootSecurity.model.Role;
import com.boot.bootSecurity.model.User;
import com.boot.bootSecurity.repositories.RoleRepository;
import com.boot.bootSecurity.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/roles/create")
    public String showCreateRoleForm(Model model) {
        model.addAttribute("role", new Role());
        return "admin-create-role";
    }

    @PostMapping("/roles/create")
    public String createRole(@ModelAttribute Role role) {
        roleRepository.save(role);
        return "redirect:/admin/roles/list";
    }

    @GetMapping("/roles/list")
    public String listRoles(Model model) {
        model.addAttribute("roles", roleRepository.findAll());
        return "admin-list-roles";
    }

    @GetMapping("/roles/edit/{id}")
    public String showEditRoleForm(@PathVariable Long id, Model model) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid role Id: " + id));

        model.addAttribute("role", role);
        return "admin-edit-role";
    }

    @PostMapping("/roles/edit/{id}")
    public String editRole(@PathVariable Long id, @ModelAttribute Role role) {
        role.setId(id);
        roleRepository.save(role);
        return "redirect:/admin/roles/list";
    }

    @GetMapping("/roles/delete/{id}")
    public String deleteRole(@PathVariable Long id) {
        roleRepository.deleteById(id);
        return "redirect:/admin/roles/list";
    }

    @GetMapping("/users/create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "admin-create-user";
    }

    @PostMapping("/users/create")
    public String createUser(@ModelAttribute User user,
                             @RequestParam List<Long> roleIds) {

        List<Role> roles = roleRepository.findAllById(roleIds);

        user.setRoles(new HashSet<>(roles));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return "redirect:/admin/users/list";
    }

    @GetMapping("/users/list")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin-list-users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));

        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepository.findAll());

        return "admin-edit-user";
    }

    @PostMapping("/users/edit/{id}")
    public String editUser(@PathVariable Long id,
                           @ModelAttribute User user,
                           @RequestParam List<Long> roleIds) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));

        existingUser.setUsername(user.getUsername());
        existingUser.setYearOfBirth(user.getYearOfBirth());

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        existingUser.setRoles(new HashSet<>(roleRepository.findAllById(roleIds)));

        userRepository.save(existingUser);

        return "redirect:/admin/users/list";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/users/list";
    }

    @GetMapping
    public String adminHome() {
        return "redirect:/admin/users/list";
    }

}