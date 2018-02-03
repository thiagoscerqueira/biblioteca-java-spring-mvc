package br.edu.fib.bibliotecajavamvc.service;

import br.edu.fib.bibliotecajavamvc.excecao.AutorPossuiLivrosAssociadosException;
import br.edu.fib.bibliotecajavamvc.model.Autor;
import br.edu.fib.bibliotecajavamvc.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> pesquisar() {
        return autorRepository.findAll(new Sort(new Sort.Order(Sort.Direction.ASC, "nome").ignoreCase()));
    }

    public void salvar(Autor autor) {
        autorRepository.save(autor);
    }

    public void excluir(Long id) throws AutorPossuiLivrosAssociadosException {
        if (autorRepository.autorSemLivros(id) == null) {
            throw new AutorPossuiLivrosAssociadosException();
        }

        this.autorRepository.delete(id);
    }

    public Autor pesquisarParaEdicao(Long id) {
        return this.autorRepository.findOne(id);
    }

}
