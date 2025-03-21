package com.pdiSpring.cadastroAutor.service;

import ch.qos.logback.core.util.StringUtil;
import com.pdiSpring.cadastroAutor.model.Autor;
import com.pdiSpring.cadastroAutor.model.Livro;
import com.pdiSpring.cadastroAutor.repository.LivroRepository;
import com.pdiSpring.cadastroAutor.repository.LivroSpec;
import com.pdiSpring.cadastroAutor.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroValidator livroValidator;

    public boolean existsByAutor(Autor autor){
        return livroRepository.existsByAutor(autor);
    }

    public void save(Livro livro) {
        livroValidator.validar(livro);
        livroRepository.save(livro);
    }

    public Optional<Livro> findById(UUID id){
        return livroRepository.findById(id);
    }

    public void deleteByLivro(Livro livro) {
        livroRepository.delete(livro);
    }

    public List<Livro> filtroLivro(String isbn, String titulo, String genero, String nomeAutor, Integer anoPublicacao){
        Specification<Livro> specs = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        if(StringUtil.notNullNorEmpty(titulo)){
            specs = specs.and(LivroSpec.tituloLike(titulo));
        }
        if(StringUtil.notNullNorEmpty(genero)){
            specs = specs.and(LivroSpec.generoEqual(genero));
        }
        if(StringUtil.notNullNorEmpty(isbn)){
            specs = specs.and(LivroSpec.isbnEqual(isbn));
        }
        if(anoPublicacao != null){
            specs = specs.and(LivroSpec.anoPublicacaoEqual(anoPublicacao));
        }
        if(StringUtil.notNullNorEmpty(nomeAutor)){
            specs = specs.and(LivroSpec.nomeAutorLike(nomeAutor));
        }

        return livroRepository.findAll(specs);
    }

    public void atualizar(Livro livro) {
        if(livro.getId() == null){
            throw  new IllegalArgumentException("Não é possivel atualizar a entidade livro com o id nulo");
        }
        save(livro);
    }
}
