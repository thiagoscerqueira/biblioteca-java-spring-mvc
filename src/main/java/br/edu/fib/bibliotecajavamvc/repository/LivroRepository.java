package br.edu.fib.bibliotecajavamvc.repository;

import br.edu.fib.bibliotecajavamvc.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long>{

    @Query("select l from Livro l where not exists (select e from l.emprestimos e where e.dataDevolucao is null) order by l.titulo")
    List<Livro> listaLivrosSemEmprestimoVigente();

    @Query("select new Livro(l.id, avg(reviews.avaliacao)) " +
            "from Livro l join l.reviews reviews " +
            "group by l.id")
    List<Livro> listaMediaAvaliacoesPorLivro();
}
