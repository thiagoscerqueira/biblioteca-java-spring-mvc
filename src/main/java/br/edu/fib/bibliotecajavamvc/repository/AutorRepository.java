package br.edu.fib.bibliotecajavamvc.repository;

import br.edu.fib.bibliotecajavamvc.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AutorRepository extends JpaRepository<Autor, Long>{

    @Query("select id from Autor a where a.livros is empty and a.id=:id")
    Long autorSemLivros(@Param("id") Long id);
}
