package com.pdiSpring.cadastroAutor.service;

import com.pdiSpring.cadastroAutor.exceptions.OperacaoNaoPermitidaException;
import com.pdiSpring.cadastroAutor.model.Autor;
import com.pdiSpring.cadastroAutor.repository.AutorRepository;
import com.pdiSpring.cadastroAutor.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator autorValidator;
    private final LivroService livroService;

    public void save(Autor autor){
        autorValidator.validar(autor);
        autorRepository.save(autor);
    }

    public void atualizar(Autor autor){
        autorValidator.validar(autor);
        if(autor == null || autor.getId() == null){
            throw  new IllegalArgumentException("OBJ AUTOR NAO ESTA PERSISTIDO");
        }
        autorRepository.save(autor);
    }

    public Optional<Autor> findById(UUID uuid){
        return  autorRepository.findById(uuid);
    }

    public void deleteByAutor(Autor autor){
        if(possuiLivroCadastrado(autor)){
            throw new OperacaoNaoPermitidaException("Não é permitido excluir um Autor que possua cadastro de livro.");
        }
        autorRepository.delete(autor);
    }


    public boolean possuiLivroCadastrado(Autor autor){
        return livroService.existsByAutor(autor);
    }

    public List<Autor> findByExample(String nome, String nacionalidade){
        Autor autor = new Autor() ;
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);
        ExampleMatcher exampleMatcher =  ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Autor> autorExample = Example.of(autor, exampleMatcher);
        return  autorRepository.findAll(autorExample);
    }



}
