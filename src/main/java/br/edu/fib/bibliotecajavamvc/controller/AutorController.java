package br.edu.fib.bibliotecajavamvc.controller;

import br.edu.fib.bibliotecajavamvc.excecao.AutorPossuiLivrosAssociadosException;
import br.edu.fib.bibliotecajavamvc.model.Autor;
import br.edu.fib.bibliotecajavamvc.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @GetMapping
    public ModelAndView autores() {
        List<Autor> autores = autorService.pesquisar();
        return new ModelAndView("autores/list", "autores", autores);
    }

    @GetMapping("/novo")
    public String novo(@ModelAttribute Autor autor) {
        return "autores/form";
    }

    @GetMapping("/{id}")
    public ModelAndView alterar(@PathVariable("id") Long id) {
        Autor autor = this.autorService.pesquisarParaEdicao(id);
        return new ModelAndView("autores/form", "autor", autor);
    }

    @PostMapping
    public ModelAndView salvar(@ModelAttribute @Valid Autor autor, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("autores/form");
        }
        autorService.salvar(autor);
        attributes.addFlashAttribute("mensagem", "Autor salvo com sucesso");
        return new ModelAndView("redirect:/autores");
    }

    @GetMapping("/excluir/{id}")
    public ModelAndView excluir(@PathVariable("id") Long id, RedirectAttributes attributes) {
        try {
            this.autorService.excluir(id);
            attributes.addFlashAttribute("mensagem", "Autor excluído com sucesso");
        } catch (AutorPossuiLivrosAssociadosException e) {
            attributes.addFlashAttribute("mensagemErro", "Autor já possui associação com livro e não pode ser excluído.");
        }

        return new ModelAndView("redirect:/autores");
    }
}
