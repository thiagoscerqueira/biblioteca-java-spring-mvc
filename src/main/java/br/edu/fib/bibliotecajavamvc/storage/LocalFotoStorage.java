package br.edu.fib.bibliotecajavamvc.storage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.FileSystems.getDefault;

@Component
public class LocalFotoStorage implements FotoStorage {

    private Path path = getDefault().getPath("fotosBiblioteca");

    @Value("${url.base.fotos}")
    private String urlBaseFotos;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(this.path);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar pastas para salvar os arquivos");
        }
    }

    @Override
    public String salvar(MultipartFile file) {

        String novoNomeArquivo = this.novoNomeArquivoParaSalvar(file.getOriginalFilename());
        try {
            file.transferTo(new File(this.path.toAbsolutePath().toString() + getDefault().getSeparator() + novoNomeArquivo));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar foto", e);
        }

        return novoNomeArquivo;
    }

    @Override
    public byte[] recuperar(String nome) {
        try {
            return Files.readAllBytes(this.path.resolve(nome));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler a foto", e);
        }
    }

    @Override
    public String getUrlFoto(String foto) {
        if (StringUtils.isEmpty(foto)) {
            return null;
        }

        return urlBaseFotos + foto;
    }
}
