package br.edu.fib.bibliotecajavamvc.service;

import br.edu.fib.bibliotecajavamvc.excecao.AutorPossuiLivrosAssociadosException;
import br.edu.fib.bibliotecajavamvc.model.Autor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AutorServiceTest {

    @Autowired
    private AutorService autorService;

    @Test
    public void devePesquisarAutores() {
        List<Autor> listaAutores = autorService.pesquisar();
        assertThat(listaAutores).hasSize(1);
    }

    @Test
    public void devePesquisarAutorParaEdicao() {
        Autor autor = autorService.pesquisarParaEdicao(1L);
        assertThat(autor).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void deveSalvarNovoAutor() {
        Autor autor = new Autor();
        autor.setNome("Novo autor");
        autor = autorService.salvar(autor);
        autor = autorService.pesquisarParaEdicao(autor.getId());
        assertThat(autor).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void deveExcluirAutorSemLivrosAssociados() throws AutorPossuiLivrosAssociadosException {
        Autor autor = new Autor();
        autor.setNome("Novo autor");
        autor = autorService.salvar(autor);
        autorService.excluir(autor.getId());

        List<Autor> listaAutores = autorService.pesquisar();
        assertThat(listaAutores).hasSize(1);
    }

    @Test(expected = AutorPossuiLivrosAssociadosException.class)
    public void naoDeveExcluirAutorComLivrosAssociados() throws AutorPossuiLivrosAssociadosException {
        autorService.excluir(1L);
    }
}