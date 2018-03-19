package br.edu.fib.bibliotecajavamvc.service;

import br.edu.fib.bibliotecajavamvc.IntegrationTests;
import br.edu.fib.bibliotecajavamvc.model.Emprestimo;
import br.edu.fib.bibliotecajavamvc.model.Livro;
import br.edu.fib.bibliotecajavamvc.model.Review;
import br.edu.fib.bibliotecajavamvc.model.Usuario;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class ReviewServiceTest extends IntegrationTests {

    @Autowired
    private EmprestimoService emprestimoService;

    @MockBean
    private IUsuarioService usuarioService;

    @Autowired
    private ReviewService reviewService;

    private Usuario usuario;
    private Livro livro;

    @Before
    public void init() {
        usuario = new Usuario(2L);
        livro = new Livro(1L, null);
        given(usuarioService.loggedUser()).willReturn(usuario);
    }

    @Test
    public void deveTrazerReviewsDoLivro() {
        criaDoisEmprestimosComDevolucao();
        List<Review> reviews = reviewService.reviewsDoLivro(livro.getId());
        assertThat(reviews).hasSize(2);
    }

    @Test
    public void deveTrazerMediasDeAvaliacoesDoLivro() {
        criaDoisEmprestimosComDevolucao();
        List<Review> reviews = reviewService.reviewsDoLivro(livro.getId());
        Double mediaDasAvaliacoesDoLivro = reviewService.mediaDasAvaliacoes(reviews);
        assertThat(mediaDasAvaliacoesDoLivro).isEqualTo(3);
    }

    private void criaDoisEmprestimosComDevolucao() {
        Emprestimo emprestimo = emprestimoService.emprestaLivro(livro.getId());
        Review review = new Review(livro, usuario, 5, "Muito bom!");
        emprestimoService.registraDevolucao(emprestimo.getId(), review);

        emprestimo = emprestimoService.emprestaLivro(livro.getId());
        review = new Review(livro, usuario, 1, "Ruim!");
        emprestimoService.registraDevolucao(emprestimo.getId(), review);
    }


}