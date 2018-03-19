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

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

public class EmprestimoServiceTest extends IntegrationTests {

    @Autowired
    private EmprestimoService emprestimoService;

    @MockBean
    private IUsuarioService usuarioService;

    private Usuario usuario;
    private Livro livro;

    @Before
    public void setUp() {
        usuario = new Usuario(2L);
        livro = new Livro(1L, null);
        given(usuarioService.loggedUser()).willReturn(usuario);
    }

    @Test
    public void deveEfetuarEmprestimoDeLivroParaOUsuarioDaBiblioteca() {
        emprestimoService.emprestaLivro(livro.getId());
        List<Emprestimo> emprestimos = emprestimoService.pesquisaEmprestimosDoUsuario();
        assertTrue("Deve ter gravado empréstimo em andamento para o usuário",
                emprestimos.stream().anyMatch(emprestimo -> emprestimo.getLivro().equals(livro) &&
                emprestimo.getDataEmprestimo() != null &&
                emprestimo.getDataDevolucao() == null &&
                emprestimo.getUsuario().equals(usuario)));
    }

    @Test
    public void deveRegistrarDevolucaoDoLivro() {
        Emprestimo emprestimo = emprestimoService.emprestaLivro(livro.getId());

        Review review = new Review(livro, usuario, 5, "Muito bom!");
        emprestimoService.registraDevolucao(emprestimo.getId(), review);

        List<Emprestimo> emprestimos = emprestimoService.pesquisaEmprestimosDoUsuario();
        assertTrue("Deve ter registrado devolução do livro",
                emprestimos.stream().anyMatch(emprestimoDoUsuario -> emprestimoDoUsuario.getLivro().equals(livro) &&
                        emprestimoDoUsuario.getDataEmprestimo() != null &&
                        emprestimoDoUsuario.getDataDevolucao() != null &&
                        emprestimoDoUsuario.getUsuario().equals(usuario)));
    }
}