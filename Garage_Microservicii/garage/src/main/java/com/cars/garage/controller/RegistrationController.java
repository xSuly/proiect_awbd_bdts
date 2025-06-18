package com.cars.garage.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.core.userdetails.User;

@Controller
public class RegistrationController {

    private final JdbcUserDetailsManager users;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(JdbcUserDetailsManager users,
                                  PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showForm() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@RequestParam String username,
                                      @RequestParam String password,
                                      @RequestParam String confirmPassword,
                                      Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords not matching.");
            return "register";
        }
        if (users.userExists(username)) {
            model.addAttribute("error", "Username alreay in use.");
            return "register";
        }

        users.createUser(User.withUsername(username)
                .password(passwordEncoder.encode(password))
                .roles("USER")
                .build()
        );
        return "redirect:/login?registered";
    }
}
