package br.edu.fib.bibliotecajavamvc.storage;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FotoStorage {

    public String salvar(MultipartFile multipartFile);

    public byte[] recuperar(String foto);

    public String getUrlFoto(String foto);

    default String novoNomeArquivoParaSalvar(String nomeOriginal) {
        return UUID.randomUUID().toString() + "_" + nomeOriginal;
    }
}
