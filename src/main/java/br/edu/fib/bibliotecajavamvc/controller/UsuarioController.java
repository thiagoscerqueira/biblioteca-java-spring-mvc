package br.edu.fib.bibliotecajavamvc.controller;

import br.edu.fib.bibliotecajavamvc.model.Usuario;
import br.edu.fib.bibliotecajavamvc.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    public ModelAndView usuarios() {
        List<Usuario> usuarios = usuarioService.pesquisar();
        return new ModelAndView("usuarios/list", "usuarios", usuarios);
    }

    @GetMapping("/novo")
    public String novo(@ModelAttribute Usuario usuario) {
        return "usuarios/form";
    }

    @GetMapping("/{id}")
    public ModelAndView alterar(@PathVariable("id") Long id) {
        Usuario usuario= this.usuarioService.pesquisarParaEdicao(id);
        return new ModelAndView("usuarios/form", "usuario", usuario);
    }

    @PostMapping
    public ModelAndView save(@ModelAttribute @Valid Usuario usuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("usuarios/form");
        }

        usuarioService.save(usuario);
        return new ModelAndView("redirect:/usuarios");
    }
}
