package br.edu.fib.bibliotecajavamvc.controller;

import br.edu.fib.bibliotecajavamvc.model.Emprestimo;
import br.edu.fib.bibliotecajavamvc.model.Livro;
import br.edu.fib.bibliotecajavamvc.model.Review;
import br.edu.fib.bibliotecajavamvc.service.AutorService;
import br.edu.fib.bibliotecajavamvc.service.EmprestimoService;
import br.edu.fib.bibliotecajavamvc.service.LivroService;
import br.edu.fib.bibliotecajavamvc.storage.FotoStorage;
import br.edu.fib.bibliotecajavamvc.validator.LivroValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController {

    @Autowired
    private LivroService livroService;

    @Autowired
    private EmprestimoService emprestimoService;

    @GetMapping
    public ModelAndView livros() {
        List<Livro> livros = livroService.pesquisarLivrosSemEmprestimoVigente();
        return new ModelAndView("emprestimo/painel", "livros", livros);
    }

    @PostMapping("/{idLivro}")
    public ModelAndView salvarEmprestimo(@PathVariable("idLivro") Long idLivro, RedirectAttributes attributes) {
        emprestimoService.emprestaLivro(idLivro);

        attributes.addFlashAttribute("mensagem", "Solicitação de empréstimo realizada com sucesso");
        return new ModelAndView("redirect:/emprestimos");
    }

    @GetMapping("/usuario")
    public ModelAndView emprestimosDoUsuario() {
        List<Emprestimo> listaEmprestimos = emprestimoService.pesquisaEmprestimosDoUsuario();
        return new ModelAndView("emprestimo/meusEmprestimosList", "emprestimos", listaEmprestimos);
    }

    @GetMapping("/devolucao/{idEmprestimo}")
    public ModelAndView devolucaoForm(@PathVariable("idEmprestimo") Long idEmprestimo) {
        ModelAndView modelAndViewDevolucao = modelAndViewDevolucaoForm(idEmprestimo, new Review());
        return modelAndViewDevolucao;
    }

    private ModelAndView modelAndViewDevolucaoForm(@PathVariable("idEmprestimo") Long idEmprestimo, Review review) {
        Emprestimo emprestimo = emprestimoService.pesquisarParaEdicao(idEmprestimo);
        ModelAndView modelAndViewDevolucao = new ModelAndView("emprestimo/devolucaoForm", "emprestimo", emprestimo);
        modelAndViewDevolucao.addObject("review", review);
        return modelAndViewDevolucao;
    }

    @PostMapping("/devolucao/{idEmprestimo}")
    public ModelAndView devolucao(@PathVariable("idEmprestimo") Long idEmprestimo, @Valid Review review,
                                  BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return modelAndViewDevolucaoForm(idEmprestimo, review);
        }

        emprestimoService.registraDevolucao(idEmprestimo, review);

        attributes.addFlashAttribute("mensagem", "Registro de devolução realizado com sucesso");
        return new ModelAndView("redirect:/emprestimos/usuario");
    }

}
