package br.edu.fib.bibliotecajavamvc.service;

import br.edu.fib.bibliotecajavamvc.model.Grupo;
import br.edu.fib.bibliotecajavamvc.model.Usuario;
import br.edu.fib.bibliotecajavamvc.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DatabaseInitializerService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostConstruct
    public void initDatabase() {
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
        if (usuarioRepository.findByUsername("admin") == null) {
            usuarioRepository.save(new Usuario(
                    "admin", encode, Grupo.ADMINISTRADOR));
        }

            if (usuarioRepository.findByUsername("usuario") == null) {
            usuarioRepository.save(new Usuario(
                    "usuario", encode, Grupo.USUARIO_BIBLIOTECA));
        }
    }
}
