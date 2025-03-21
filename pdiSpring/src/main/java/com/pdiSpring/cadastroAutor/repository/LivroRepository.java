package com.pdiSpring.cadastroAutor.repository;

import com.pdiSpring.cadastroAutor.model.Autor;
import com.pdiSpring.cadastroAutor.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {

    List<Livro> findByAutor (Autor autor);

    List<Livro> findByTitulo(String titulo);

    List<Livro> findByIsbn (String isbn);

    List<Livro> findByTituloAndAutor(String titulo, Autor autor);

    List<Livro> findByPreco(Double preco);

    List<Livro> findByTituloAndPreco(String titulo, Double preco);

    List<Livro> findByTituloOrPreco(String titulo, Double preco);

    List<Livro> findByDataPublicacaoBetweenOrderByDataPublicacaoDesc(LocalDate inicio, LocalDate fim);

    @Query("select l from Livro as l order by l.titulo")
    List<Livro> listarTodos();

    @Query("""
            select l 
            from Livro as l
            where 
            l.genero = :genero
            order by :paramOrdenacao 
            """)
    List<Livro> findByGeneroPositionalNamedParameters(@Param("genero") String genero, @Param("paramOrdenacao") String paramOrdenacao);

    @Query("""
            select l a
            from Livro as l
            where 
            l.titulo = ?1
            order by ?2 
            """)
    List<Livro> findByTituloPositionalParameters(String genero, String paramOrdenacao);

    boolean existsByAutor(Autor autor);

    Optional<Livro> findByTituloOrIsbn(String titulo, String isbn);

}
