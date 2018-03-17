package br.edu.fib.bibliotecajavamvc.service;

import br.edu.fib.bibliotecajavamvc.model.Emprestimo;
import br.edu.fib.bibliotecajavamvc.model.Livro;
import br.edu.fib.bibliotecajavamvc.model.Review;
import br.edu.fib.bibliotecajavamvc.model.Usuario;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmprestimoServiceTest {

    @Autowired
    private EmprestimoService emprestimoService;

    @MockBean
    private IUsuarioService usuarioService;

    private Usuario usuario;
    private Livro livro;

    @Before
    public void init() {
        usuario = new Usuario(2L);
        livro = new Livro(1L, null);
        given(usuarioService.loggedUser()).willReturn(usuario);
    }

    @Test
    @Transactional
    @Rollback
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
    @Transactional
    @Rollback
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