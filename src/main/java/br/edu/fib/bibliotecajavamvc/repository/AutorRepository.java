package br.edu.fib.bibliotecajavamvc.repository;

import br.edu.fib.bibliotecajavamvc.model.Autor;
import br.edu.fib.bibliotecajavamvc.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long>{
}
