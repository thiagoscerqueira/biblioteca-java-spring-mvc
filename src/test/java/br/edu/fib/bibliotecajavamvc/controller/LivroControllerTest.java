package br.edu.fib.bibliotecajavamvc.controller;

import br.edu.fib.bibliotecajavamvc.IntegrationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class LivroControllerTest extends IntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMINISTRADOR"})
    public void deveChamarPesquisaLivros() throws Exception {
        this.mockMvc.perform(get("/livros")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMINISTRADOR"})
    public void deveChamarNovoLivro() throws Exception {
        this.mockMvc.perform(get("/livros/novo")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMINISTRADOR"})
    public void deveChamarEdicaoLivro() throws Exception {
        this.mockMvc.perform(get("/livros/1")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMINISTRADOR"})
    public void deveSalvarNovoLivro() throws Exception {
        MockMultipartFile mockedFile = new MockMultipartFile("fotoUpload", "arquivo.jpeg", "image/jpeg",
                new byte[]{});

        this.mockMvc.perform(fileUpload("/livros")
                .file(mockedFile)
                .param("titulo","Investimentos inteligentes")
                .param("quantidade","20")
                .param("autor.id","1")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/livros"))
                .andExpect(flash().attribute("mensagem", "Livro salvo com sucesso"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMINISTRADOR"})
    public void naoDeveSalvarNovoLivroQuandoNaoInformaFoto() throws Exception {
        this.mockMvc.perform(post("/livros")
                .param("titulo","Investimentos inteligentes")
                .param("quantidade","20")
                .param("autor.id","1")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Deve ser selecionado um arquivo no formato jpeg")));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMINISTRADOR"})
    public void naoDeveSalvarNovoLivroQuandoInformaFotoComFormatoInvalido() throws Exception {
        MockMultipartFile mockedFile = new MockMultipartFile("fotoUpload", "arquivo.jpeg", "image/png",
                new byte[]{});

        this.mockMvc.perform(fileUpload("/livros")
                .file(mockedFile)
                .param("titulo","Investimentos inteligentes")
                .param("quantidade","20")
                .param("autor.id","1")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("O formato do arquivo selecionado deve ser jpeg")));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMINISTRADOR"})
    public void naoDeveSalvarNovoLivroQuandoNaoInformaTitulo() throws Exception {
        MockMultipartFile mockedFile = new MockMultipartFile("fotoUpload", "arquivo.jpeg", "image/jpeg",
                new byte[]{});

        this.mockMvc.perform(fileUpload("/livros")
                .file(mockedFile)
                .param("quantidade","20")
                .param("autor.id","1")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Título: Campo obrigatório")));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMINISTRADOR"})
    public void naoDeveSalvarNovoLivroQuandoNaoInformaQuantidade() throws Exception {
        MockMultipartFile mockedFile = new MockMultipartFile("fotoUpload", "arquivo.jpeg", "image/jpeg",
                new byte[]{});

        this.mockMvc.perform(fileUpload("/livros")
                .file(mockedFile)
                .param("titulo","Investimentos inteligentes")
                .param("autor.id","1")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Total Páginas: Campo obrigatório")));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMINISTRADOR"})
    public void naoDeveSalvarNovoLivroQuandoInformaQuantidadeComValorMenorQue10() throws Exception {
        MockMultipartFile mockedFile = new MockMultipartFile("fotoUpload", "arquivo.jpeg", "image/jpeg",
                new byte[]{});

        this.mockMvc.perform(fileUpload("/livros")
                .file(mockedFile)
                .param("titulo","Investimentos inteligentes")
                .param("quantidade","9")
                .param("autor.id","1")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Total Páginas: O numero mínimo é 10")));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMINISTRADOR"})
    public void naoDeveSalvarNovoLivroQuandoNaoInformaAutor() throws Exception {
        MockMultipartFile mockedFile = new MockMultipartFile("fotoUpload", "arquivo.jpeg", "image/jpeg",
                new byte[]{});

        this.mockMvc.perform(fileUpload("/livros")
                .file(mockedFile)
                .param("titulo","Investimentos inteligentes")
                .param("quantidade","20")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Obrigatório selecionar um autor")));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMINISTRADOR"})
    public void deveExcluirLivro() throws Exception {
        this.mockMvc.perform(get("/livros/excluir/1")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/livros"))
                .andExpect(flash().attribute("mensagem", "Livro excluído com sucesso"));
    }

    @Test
    @WithMockUser(username = "usuario", roles = {"USUARIO_BIBLIOTECA"})
    public void deveProibirAcessoSeUsuarioBibliotecaAcessaPesquisaLivros() throws Exception {
        this.mockMvc.perform(get("/livros")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "usuario", roles = {"USUARIO_BIBLIOTECA"})
    public void deveProibirAcessoSeUsuarioBibliotecaAcessaEdicaoLivro() throws Exception {
        this.mockMvc.perform(get("/livros/1")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "usuario", roles = {"USUARIO_BIBLIOTECA"})
    public void deveProibirAcessoSeUsuarioBibliotecaTentaSalvarLivro() throws Exception {
        MockMultipartFile mockedFile = new MockMultipartFile("fotoUpload", "arquivo.jpeg", "image/jpeg",
                new byte[]{});

        this.mockMvc.perform(fileUpload("/livros")
                .file(mockedFile)
                .param("titulo","Investimentos inteligentes")
                .param("quantidade","20")
                .param("autor.id","1")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "usuario", roles = {"USUARIO_BIBLIOTECA"})
    public void deveProibirAcessoSeUsuarioBibliotecaTentaExcluirLivro() throws Exception {
        this.mockMvc.perform(get("/livros/excluir/1")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isForbidden());
    }


}