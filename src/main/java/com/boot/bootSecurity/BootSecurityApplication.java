package com.boot.bootSecurity;

import com.boot.bootSecurity.model.Role;
import com.boot.bootSecurity.model.User;
import com.boot.bootSecurity.repositories.RoleRepository;
import com.boot.bootSecurity.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication
public class BootSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootSecurityApplication.class, args);
    }


    @Bean
    public CommandLineRunner initUsers(UserRepository userRepo,
                                       RoleRepository roleRepo,
                                       PasswordEncoder encoder) {
        return args -> {


            userRepo.deleteAll();
            roleRepo.deleteAll();


            Role adminRole = new Role("ROLE_ADMIN");
            Role userRole = new Role("ROLE_USER");

            roleRepo.save(adminRole);
            roleRepo.save(userRole);


            User admin = new User("admin", 1985, encoder.encode("admin"));
            admin.setRoles(Set.of(adminRole, userRole));
            userRepo.save(admin);

            User user = new User("user", 1990, encoder.encode("user"));
            user.setRoles(Set.of(userRole));
            userRepo.save(user);


            userRepo.findAll().forEach(u -> {
                System.out.println(u.getUsername() + " -> " +
                        u.getRoles().stream()
                                .map(Role::getName)
                                .collect(Collectors.joining(",")));
            });

        };
    }

}