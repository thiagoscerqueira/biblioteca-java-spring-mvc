package br.edu.fib.bibliotecajavamvc.service;

import br.edu.fib.bibliotecajavamvc.model.Emprestimo;
import br.edu.fib.bibliotecajavamvc.model.Livro;
import br.edu.fib.bibliotecajavamvc.model.Review;
import br.edu.fib.bibliotecajavamvc.repository.EmprestimoRepository;
import br.edu.fib.bibliotecajavamvc.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private IUsuarioService usuarioService;

    public void emprestaLivro(Long idLivro) {
        Livro livro = new Livro();
        livro.setId(idLivro);

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setDataEmprestimo(Calendar.getInstance().getTime());
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuarioService.loggedUser());

        emprestimoRepository.save(emprestimo);
    }

    public List<Emprestimo> pesquisaEmprestimosDoUsuario() {
        return emprestimoRepository.findByUsuarioOrderByDataEmprestimoDesc(usuarioService.loggedUser());
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void registraDevolucao(Long idEmprestimo, Review review) {
        Emprestimo emprestimo = emprestimoRepository.findOne(idEmprestimo);
        emprestimo.setDataDevolucao(Calendar.getInstance().getTime());
        emprestimoRepository.save(emprestimo);

        review.setLivro(emprestimo.getLivro());
        review.setUsuario(usuarioService.loggedUser());

        reviewRepository.save(review);
    }

    public Emprestimo pesquisarParaEdicao(Long id) {
        return this.emprestimoRepository.findOne(id);
    }
}
