package br.edu.fib.bibliotecajavamvc.service;

import br.edu.fib.bibliotecajavamvc.IntegrationTests;
import br.edu.fib.bibliotecajavamvc.model.Autor;
import br.edu.fib.bibliotecajavamvc.model.Livro;
import br.edu.fib.bibliotecajavamvc.model.Usuario;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class LivroServiceTest extends IntegrationTests {

    @Autowired
    private LivroService livroService;

    @Autowired
    private EmprestimoService emprestimoService;

    @MockBean
    private IUsuarioService usuarioService;

    @Test
    public void devePesquisarLivroCadastradoParaEdicao() {
        Livro livro = livroService.pesquisarParaEdicao(1L);
        assertThat(livro).isNotNull();
    }

    @Test
    public void devePesquisarTodosLivrosCadastrados() {
        List<Livro> livros = livroService.pesquisar();
        assertThat(livros).hasSize(2);
    }

    @Test
    public void devePesquisarLivrosSemEmprestimoVigente() {
        Usuario usuario = new Usuario(2L);
        given(usuarioService.loggedUser()).willReturn(usuario);
        emprestimoService.emprestaLivro(1L);

        List<Livro> livros = livroService.pesquisarLivrosSemEmprestimoVigente();
        assertThat(livros).hasSize(1);
    }

    @Test
    public void deveInserirNovoLivro() {
        Livro livro =
                new Livro("Novo Livro", null, 200, "jfdjsjf", new Autor(1L), null);
        livro.setFotoUpload(new MockMultipartFile("arquivo.jpeg", "arquivo.jpeg", "jpeg",
                new byte[]{}));
        livroService.salvar(livro);
        List<Livro> livros = livroService.pesquisar();
        assertThat(livros).hasSize(3);
    }
}