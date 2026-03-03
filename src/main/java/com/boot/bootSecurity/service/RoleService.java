package com.boot.bootSecurity.service;


import com.boot.bootSecurity.model.Role;
import com.boot.bootSecurity.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    public void createRole(Role role) {roleRepository.save(role);
    }


    public Role getRole(Long id) {return roleRepository.findById(id)
         .orElseThrow(() -> new RuntimeException("Role not found with id " + id));
    }

    public void updateRole(Long id, Role role) {
        Role existingRole = roleRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Role not found with id " + id));
        existingRole.setName(role.getName());
        roleRepository.save(existingRole);
    }
}