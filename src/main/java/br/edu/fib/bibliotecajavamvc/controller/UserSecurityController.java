package br.edu.fib.bibliotecajavamvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserSecurityController {

    @GetMapping("/login")
    public String login() {
        return "/user-security/Login";
    }

}
