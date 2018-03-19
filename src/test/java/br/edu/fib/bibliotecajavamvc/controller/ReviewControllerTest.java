package br.edu.fib.bibliotecajavamvc.controller;

import br.edu.fib.bibliotecajavamvc.IntegrationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WithMockUser(username = "usuario", roles = {"USUARIO_BIBLIOTECA"})
public class ReviewControllerTest extends IntegrationTests{

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveChamarPesquisaReviewsDeLivro() throws Exception {
        this.mockMvc.perform(get("/reviews/1")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

}