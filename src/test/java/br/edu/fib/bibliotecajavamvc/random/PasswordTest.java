package br.edu.fib.bibliotecajavamvc.random;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {

    @Test
    public void printPass() {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }
}
