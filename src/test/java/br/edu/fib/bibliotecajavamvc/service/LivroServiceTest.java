package br.edu.fib.bibliotecajavamvc.service;

import br.edu.fib.bibliotecajavamvc.IntegrationTests;
import br.edu.fib.bibliotecajavamvc.model.Autor;
import br.edu.fib.bibliotecajavamvc.model.Livro;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LivroServiceTest extends IntegrationTests {

    @Autowired
    private LivroService livroService;

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
        List<Livro> livros = livroService.pesquisarLivrosSemEmprestimoVigente();
        assertThat(livros).hasSize(1);
    }

    @Test
    public void deveInserirNovoLivro() {
        Livro livro =
                new Livro("Novo Livro", null, 200, "jfdjsjf", new Autor(1L), null);
        livro.setFotoUpload(new MockMultipartFile("arquivo.jpeg", "arquivo.jpeg", "image/jpeg",
                new byte[]{}));
        livroService.salvar(livro);
        List<Livro> livros = livroService.pesquisar();
        assertThat(livros).hasSize(3);
    }
}