package com.pdiSpring.cadastroAutor.validator;

import com.pdiSpring.cadastroAutor.exceptions.RegistroDuplicadoException;
import com.pdiSpring.cadastroAutor.model.Autor;
import com.pdiSpring.cadastroAutor.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AutorValidator {


    private final AutorRepository autorRepository;


    public void validar(Autor autor){
        if(existAutorCadastrado(autor)){
            throw  new RegistroDuplicadoException("Autor já cadastrado! TESTE PDI");
        }
    }

    private boolean existAutorCadastrado(Autor autor){
        Optional<Autor> autorOptional = autorRepository.findByNomeAndDataNascimentoAndNacionalidade(autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());
        if(autor.getId() == null){
            return autorOptional.isPresent();
        }
        return autorOptional.isPresent() && !autor.getId().equals(autorOptional.get().getId());

    }


}
