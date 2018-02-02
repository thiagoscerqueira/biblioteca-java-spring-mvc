package br.edu.fib.bibliotecajavamvc.repository;

import br.edu.fib.bibliotecajavamvc.model.Autor;
import br.edu.fib.bibliotecajavamvc.model.Emprestimo;
import br.edu.fib.bibliotecajavamvc.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long>{

    List<Emprestimo> findByUsuarioOrderByDataEmprestimoDesc(@Param("usuario") Usuario usuario);
}
