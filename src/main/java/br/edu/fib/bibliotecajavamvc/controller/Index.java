package br.edu.fib.bibliotecajavamvc.controller;

import br.edu.fib.bibliotecajavamvc.model.Grupo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Index {

    @RequestMapping("/")
    public String home(HttpServletRequest request) {
        if (request.isUserInRole(Grupo.USUARIO_BIBLIOTECA.name())) {
            return "redirect:/emprestimos";
        }
        return "index";
    }
}
