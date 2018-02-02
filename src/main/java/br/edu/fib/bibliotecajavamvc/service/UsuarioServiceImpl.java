package br.edu.fib.bibliotecajavamvc.service;

import br.edu.fib.bibliotecajavamvc.model.Usuario;
import br.edu.fib.bibliotecajavamvc.repository.UsuarioRepository;
import br.edu.fib.bibliotecajavamvc.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> pesquisar() {
        return usuarioRepository.todosComGrupos();
    }

    @Override
    public Usuario pesquisarParaEdicao(Long id) {
        return this.usuarioRepository.findOne(id);
    }

    @Override
    public void save(Usuario usuario) {
        if (!CollectionUtils.isEmpty(usuario.getGrupos())) {
            usuario.getGrupos().get(0).setUsuario(usuario);
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
    }

    @Override
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public Usuario loggedUser() {
        if (securityService.findLoggedInUser() == null) {
            return null;
        }

        return findByUsername(securityService.findLoggedInUser().getUsername());
    }
}
