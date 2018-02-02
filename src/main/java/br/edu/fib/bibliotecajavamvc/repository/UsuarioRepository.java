package br.edu.fib.bibliotecajavamvc.repository;

import br.edu.fib.bibliotecajavamvc.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    @Query("select u from Usuario u join fetch u.grupos grupos where u.username=:username")
    Usuario findByUsername(@Param("username") String username);

    @Query("select u from Usuario u join fetch u.grupos grupos order by u.username")
    List<Usuario> todosComGrupos();
    
}
