package br.edu.fib.bibliotecajavamvc.repository;

import br.edu.fib.bibliotecajavamvc.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>{

    @Query("select r from Review r where r.livro.id=:idLivro order by r.id desc")
    public List<Review> reviewsDoLivro(@Param("idLivro") Long idLivro);
}
