package com.pdiSpring.cadastroAutor.controller.dto;

import com.pdiSpring.cadastroAutor.model.Autor;
import com.pdiSpring.cadastroAutor.model.Livro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(

        @ISBN
        @NotBlank(message = "O campo é obrigatório")
        String isbn,
        @NotBlank(message = "O campo é obrigatório")
        String titulo,
        @NotNull(message = "O campo é obrigatório")
        @Past(message = "não pode ser uma data futura")
        LocalDate dtPublicacao,
        @NotBlank(message = "O campo é obrigatório")
        String genero,
        Double preco,
        @NotNull(message = "O campo é obrigatório")
        UUID idAutor) {

        public Livro dtoToLivro(CadastroLivroDTO dto){
                Livro livro = new Livro();
                livro.setIsbn(dto.isbn);
                livro.setAutor(new Autor(dto.idAutor));
                livro.setGenero(dto.genero);
                livro.setTitulo(dto.titulo);
                livro.setDataPublicacao(dto.dtPublicacao);
                livro.setPreco(dto.preco);
                return livro;
        }
}
