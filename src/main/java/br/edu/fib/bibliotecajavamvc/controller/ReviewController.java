package br.edu.fib.bibliotecajavamvc.controller;

import br.edu.fib.bibliotecajavamvc.model.Review;
import br.edu.fib.bibliotecajavamvc.service.LivroService;
import br.edu.fib.bibliotecajavamvc.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private LivroService livroService;

    @GetMapping("/{idLivro}")
    public ModelAndView reviews(@PathVariable("idLivro") Long idLivro) {
        List<Review> reviews = reviewService.reviewsDoLivro(idLivro);
        ModelAndView modelAndView = new ModelAndView("reviews/list", "reviews", reviews);
        modelAndView.addObject("mediaDasAvaliacoes", reviewService.mediaDasAvaliacoes(reviews));
        modelAndView.addObject("livro", livroService.pesquisarParaEdicao(idLivro));
        return modelAndView;
    }
}
