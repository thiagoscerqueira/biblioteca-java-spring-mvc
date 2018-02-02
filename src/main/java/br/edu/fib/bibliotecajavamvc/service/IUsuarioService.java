package br.edu.fib.bibliotecajavamvc.service;

import br.edu.fib.bibliotecajavamvc.model.Usuario;

import java.util.List;

public interface IUsuarioService {

    List<Usuario> pesquisar();

    Usuario pesquisarParaEdicao(Long id);

    void save(Usuario usuario);

    Usuario findByUsername(String username);

    Usuario loggedUser();
}
