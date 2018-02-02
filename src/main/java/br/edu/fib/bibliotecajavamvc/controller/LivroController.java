package br.edu.fib.bibliotecajavamvc.controller;

import br.edu.fib.bibliotecajavamvc.model.Livro;
import br.edu.fib.bibliotecajavamvc.service.AutorService;
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
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroValidator livroValidator;

    @Autowired
    private FotoStorage fotoStorage;

    @Autowired
    private LivroService livroService;

    @Autowired
    private AutorService autorService;

    @GetMapping
    public ModelAndView livros() {
        List<Livro> livros = livroService.pesquisar();
        return new ModelAndView("livros/list", "livros", livros);
    }

    @GetMapping("/novo")
    public ModelAndView novo(@ModelAttribute Livro livro) {
        return modelAndViewCadastro(livro);
    }

    @GetMapping("/{id}")
    public ModelAndView alterar(@PathVariable("id") Long id) {
        Livro livro = this.livroService.pesquisarParaEdicao(id);
        return modelAndViewCadastro(livro);
    }

    private ModelAndView modelAndViewCadastro(Livro livro) {
        ModelAndView model = new ModelAndView("livros/form", "livro", livro);
        model.addObject("autores", autorService.pesquisar());
        model.addObject("urlFoto", fotoStorage.getUrlFoto(livro.getFoto()));
        return model;
    }

    @PostMapping
    public ModelAndView salvar(@Valid Livro livro, BindingResult bindingResult, RedirectAttributes attributes) {

        livroValidator.validate(livro, bindingResult);

        if (bindingResult.hasErrors()) {
            return modelAndViewCadastro(livro);
        }

        livroService.salvar(livro);
        attributes.addFlashAttribute("mensagem", "Livro salvo com sucesso");

        return new ModelAndView("redirect:/livros");
    }

    @GetMapping("/excluir/{id}")
    public ModelAndView excluir(@PathVariable("id") Long id, RedirectAttributes attributes) {
        this.livroService.excluir(id);

        attributes.addFlashAttribute("mensagem", "Livro excluído com sucesso");
        return new ModelAndView("redirect:/livros");
    }

    @GetMapping("/foto/{nome:.*}")
    public ResponseEntity<byte[]> recuperar(@PathVariable String nome) {
        return new ResponseEntity<>(fotoStorage.recuperar(nome), HttpStatus.OK);
    }

}
