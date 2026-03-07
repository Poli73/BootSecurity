package com.boot.bootSecurity.controllers;

import com.boot.bootSecurity.model.Role;
import com.boot.bootSecurity.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/list")
    public String listRoles(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin-list-roles";
    }

    @GetMapping("/create")
    public String showCreateRoleForm(Model model) {
        model.addAttribute("role", new Role());
        return "admin-create-role";
    }

    @PostMapping("/create")
    public String createRole(@ModelAttribute Role role) {
        roleService.createRole(role);
        return "redirect:/admin/roles/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditRoleForm(@PathVariable Long id, Model model) {
        model.addAttribute("role", roleService.getRole(id));
        return "admin-edit-role";
    }

    @PostMapping("/edit/{id}")
    public String updateRole(@PathVariable Long id, @ModelAttribute Role role) {
        roleService.updateRole(id, role);
        return "redirect:/admin/roles/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return "redirect:/admin/roles/list";
    }
}