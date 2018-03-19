package br.edu.fib.bibliotecajavamvc.controller;

import br.edu.fib.bibliotecajavamvc.IntegrationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class AutorControllerTest extends IntegrationTests  {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMINISTRADOR"})
    public void deveChamarPesquisaAutores() throws Exception {
        this.mockMvc.perform(get("/autores")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMINISTRADOR"})
    public void deveChamarNovoAutor() throws Exception {
        this.mockMvc.perform(get("/autores/novo")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMINISTRADOR"})
    public void deveChamarEdicaoAutor() throws Exception {
        this.mockMvc.perform(get("/autores/1")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMINISTRADOR"})
    public void deveSalvarNovoAutor() throws Exception {
        this.mockMvc.perform(post("/autores")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("nome","Thiago Cerqueira")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/autores"))
                .andExpect(flash().attribute("mensagem", "Autor salvo com sucesso"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMINISTRADOR"})
    public void deveExcluirAutor() throws Exception {
        this.mockMvc.perform(get("/autores/excluir/2")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/autores"))
                .andExpect(flash().attribute("mensagem", "Autor excluído com sucesso"));
    }

    @Test
    @WithMockUser(username = "usuario", roles = {"USUARIO_BIBLIOTECA"})
    public void deveProibirAcessoSeUsuarioBibliotecaAcessaPesquisaAutores() throws Exception {
        this.mockMvc.perform(get("/autores")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "usuario", roles = {"USUARIO_BIBLIOTECA"})
    public void deveProibirAcessoSeUsuarioBibliotecaAcessaEdicaoAutor() throws Exception {
        this.mockMvc.perform(get("/autores/1")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "usuario", roles = {"USUARIO_BIBLIOTECA"})
    public void deveProibirAcessoSeUsuarioBibliotecaTentaSalvarAutor() throws Exception {
        this.mockMvc.perform(post("/autores")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("nome","Thiago Cerqueira")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "usuario", roles = {"USUARIO_BIBLIOTECA"})
    public void deveProibirAcessoSeUsuarioBibliotecaTentaExcluirAutor() throws Exception {
        this.mockMvc.perform(get("/autores/excluir/2")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isForbidden());
    }

}