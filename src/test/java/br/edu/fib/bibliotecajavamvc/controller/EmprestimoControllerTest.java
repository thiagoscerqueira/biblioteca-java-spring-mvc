package br.edu.fib.bibliotecajavamvc.controller;

import br.edu.fib.bibliotecajavamvc.IntegrationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WithMockUser(username = "usuario", roles = {"USUARIO_BIBLIOTECA"})
public class EmprestimoControllerTest extends IntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveChamarPesquisaLivrosParaEmprestimo() throws Exception {
        this.mockMvc.perform(get("/emprestimos")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    public void deveChamarPesquisaEmprestimosDoUsuario() throws Exception {
        this.mockMvc.perform(get("/emprestimos/usuario")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    public void deveSalvarNovoEmprestimoDeLivro() throws Exception {
        this.mockMvc.perform(post("/emprestimos/1")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/emprestimos"))
                .andExpect(flash().attribute("mensagem", "Solicitação de empréstimo realizada com sucesso"));
    }

    @Test
    public void deveChamarEdicaoDeDevolucaoDeLivro() throws Exception {
        this.mockMvc.perform(get("/emprestimos/devolucao/1")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    public void deveSalvarDevolucaoDeLivro() throws Exception {
        this.mockMvc.perform(post("/emprestimos/devolucao/1")
                .param("avaliacao", "3")
                .param("comentario", "Bom livro. Poderia ter letras maiores.")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/emprestimos/usuario"))
                .andExpect(flash().attribute("mensagem", "Registro de devolução realizado com sucesso"));
    }

    @Test
    public void naoDeveSalvarDevolucaoDeLivroQuandoNaoInformaAvaliacao() throws Exception {
        this.mockMvc.perform(post("/emprestimos/devolucao/1")
                .param("comentario", "Bom livro. Poderia ter letras maiores.")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Avaliação: O numero mínimo é 1")));
    }

    @Test
    public void naoDeveSalvarDevolucaoDeLivroQuandoNaoInformaComentario() throws Exception {
        this.mockMvc.perform(post("/emprestimos/devolucao/1")
                .param("avaliacao", "3")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Comentário: Campo obrigatório")));
    }


}