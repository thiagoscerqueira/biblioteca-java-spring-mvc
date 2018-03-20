package br.edu.fib.bibliotecajavamvc.selenium;

import br.edu.fib.bibliotecajavamvc.IntegrationTests;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmprestimoTest extends IntegrationTests {

    private WebDriver driver;

    @LocalServerPort
    private int port;

    private String contextRoot;

    @Before
    public void setUp() throws IOException {
        driver = new DriverLoader().load();
        contextRoot = "http://localhost:" + port;
    }

    @Test
    public void deveEfetuarEmprestimo() {
        driver.get(contextRoot);

        WebElement campoUsuario = driver.findElement(By.name("username"));
        campoUsuario.sendKeys("usuario");
        WebElement campoSenha = driver.findElement(By.name("password"));
        campoSenha.sendKeys("123456");
        campoSenha.submit();

        driver.get(contextRoot + "/emprestimos");
        WebElement elementoFormAcaoEmprestimoLivroComId1 = driver.findElement(By.id("emprestimosForm_1"));
        elementoFormAcaoEmprestimoLivroComId1.submit();

        assertTrue("Deve disparar mensagem de empréstimo realizado com sucesso",
                driver.getPageSource().contains("Solicitação de empréstimo realizada com sucesso"));

        driver.close();
    }
}
