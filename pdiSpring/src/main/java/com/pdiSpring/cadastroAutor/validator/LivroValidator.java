package com.pdiSpring.cadastroAutor.validator;

import com.pdiSpring.cadastroAutor.exceptions.RegistroDuplicadoException;
import com.pdiSpring.cadastroAutor.model.Autor;
import com.pdiSpring.cadastroAutor.model.Livro;
import com.pdiSpring.cadastroAutor.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private final LivroRepository livroRepository;


    public void validar(Livro livro){
        if(existLivroCadastrado(livro)){
            throw  new RegistroDuplicadoException("Livro já cadastrado!");
        }
    }

    private boolean existLivroCadastrado(Livro livro){
        Optional<Livro> autorOptional = livroRepository.findByTituloOrIsbn(livro.getTitulo(), livro.getIsbn());
        if(livro.getId() == null){
            return autorOptional.isPresent();
        }
        return autorOptional.isPresent() && !livro.getId().equals(autorOptional.get().getId());

    }
}
