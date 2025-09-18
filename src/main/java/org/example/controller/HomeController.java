package org.example.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Principal p, Model m) {
        m.addAttribute("username", p != null ? p.getName() : "Guest");
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String denied() {
        return "access-denied";
    }
}

