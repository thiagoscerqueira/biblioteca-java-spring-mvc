package br.edu.fib.bibliotecajavamvc.service;

import br.edu.fib.bibliotecajavamvc.model.Livro;
import br.edu.fib.bibliotecajavamvc.repository.LivroRepository;
import br.edu.fib.bibliotecajavamvc.storage.FotoStorage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private FotoStorage fotoStorage;

    public List<Livro> pesquisar() {
        return livroRepository.findAll(new Sort(new Sort.Order(Sort.Direction.ASC, "titulo").ignoreCase()));
    }

    public List<Livro> pesquisarLivrosSemEmprestimoVigente() {
        return livroRepository.listaLivrosSemEmprestimoVigente();
    }

    public void salvar(Livro livro) {
        salvaFotoSeSelecionou(livro);
        livroRepository.save(livro);
    }

    private void salvaFotoSeSelecionou(Livro livro) {
        if (!livro.fotoVazia()) {
            String nomeArquivoFoto = fotoStorage.salvar(livro.getFotoUpload());
            livro.setFoto(nomeArquivoFoto);
        }
    }

    public void excluir(Long id) {
        this.livroRepository.delete(id);
    }

    public Livro pesquisarParaEdicao(Long id) {
        return this.livroRepository.findOne(id);
    }

}
