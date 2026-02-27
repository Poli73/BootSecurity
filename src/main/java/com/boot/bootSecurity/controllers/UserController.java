package com.boot.bootSecurity.controllers;

import com.boot.bootSecurity.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/user")
    public String userPage(Model model,
                           @AuthenticationPrincipal User user){

        model.addAttribute("user", user);

        return "user";
    }

}