package br.edu.fib.bibliotecajavamvc.service;

import br.edu.fib.bibliotecajavamvc.excecao.LivroPossuiAssociacoesException;
import br.edu.fib.bibliotecajavamvc.model.Livro;
import br.edu.fib.bibliotecajavamvc.repository.LivroRepository;
import br.edu.fib.bibliotecajavamvc.storage.FotoStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<Livro> livros = livroRepository.listaLivrosSemEmprestimoVigente();
        preencheMediaDasAvaliacoes(livros);
        return livros;
    }

    private void preencheMediaDasAvaliacoes(List<Livro> livros) {
        List<Livro> livrosComMediaDeAvaliacoes = livroRepository.listaMediaAvaliacoesPorLivro();
        livros.forEach(livro -> livro.setMediaAvaliacoes(
                livrosComMediaDeAvaliacoes.stream().filter(livroAvaliacao -> livroAvaliacao.getId().equals(livro.getId()))
                        .findFirst().orElse(new Livro()).getMediaAvaliacoes())
        );
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

    public void excluir(Long id) throws LivroPossuiAssociacoesException {
        if (livroRepository.livroSemAssociacoes(id) == null) {
            throw new LivroPossuiAssociacoesException();
        }

        this.livroRepository.delete(id);
    }

    public Livro pesquisarParaEdicao(Long id) {
        return this.livroRepository.findOne(id);
    }

}
