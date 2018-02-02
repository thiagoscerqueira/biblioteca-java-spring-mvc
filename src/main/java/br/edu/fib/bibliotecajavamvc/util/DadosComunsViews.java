package br.edu.fib.bibliotecajavamvc.util;

import br.edu.fib.bibliotecajavamvc.storage.FotoStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DadosComunsViews {

    @Autowired
    private FotoStorage fotoStorage;

    public String getUrlFoto(String nomeFoto) {
        return fotoStorage.getUrlFoto(nomeFoto);
    }
}
