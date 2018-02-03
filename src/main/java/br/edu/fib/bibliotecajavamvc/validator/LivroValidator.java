package br.edu.fib.bibliotecajavamvc.validator;

import br.edu.fib.bibliotecajavamvc.model.Livro;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LivroValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Livro.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Livro livro = (Livro) o;

        if (!livro.temFotoCadastrada() && livro.fotoVazia()) {
            errors.rejectValue("fotoUpload", "arquivo.nome.vazio");
            return;
        }

        if (!livro.fotoVazia() && !livro.formatoFotoValido()) {
            errors.rejectValue("fotoUpload", "arquivo.formato.invalido");
        }
    }
}
