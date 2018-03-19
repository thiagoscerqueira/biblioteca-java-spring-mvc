package br.edu.fib.bibliotecajavamvc.service;

import br.edu.fib.bibliotecajavamvc.IntegrationTests;
import br.edu.fib.bibliotecajavamvc.excecao.AutorPossuiLivrosAssociadosException;
import br.edu.fib.bibliotecajavamvc.model.Autor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AutorServiceTest extends IntegrationTests {

    @Autowired
    private AutorService autorService;

    @Test
    public void devePesquisarAutores() {
        List<Autor> listaAutores = autorService.pesquisar();
        assertThat(listaAutores).hasSize(2);
    }

    @Test
    public void devePesquisarAutorParaEdicao() {
        Autor autor = autorService.pesquisarParaEdicao(1L);
        assertThat(autor).isNotNull();
    }

    @Test
    public void deveSalvarNovoAutor() {
        Autor autor = new Autor();
        autor.setNome("Novo autor");
        autor = autorService.salvar(autor);
        autor = autorService.pesquisarParaEdicao(autor.getId());
        assertThat(autor).isNotNull();
    }

    @Test
    public void deveExcluirAutorSemLivrosAssociados() throws AutorPossuiLivrosAssociadosException {
        Autor autor = new Autor();
        autor.setNome("Novo autor");
        autor = autorService.salvar(autor);
        autorService.excluir(autor.getId());

        List<Autor> listaAutores = autorService.pesquisar();
        assertThat(listaAutores).hasSize(2);
    }

    @Test(expected = AutorPossuiLivrosAssociadosException.class)
    public void naoDeveExcluirAutorComLivrosAssociados() throws AutorPossuiLivrosAssociadosException {
        autorService.excluir(1L);
    }
}