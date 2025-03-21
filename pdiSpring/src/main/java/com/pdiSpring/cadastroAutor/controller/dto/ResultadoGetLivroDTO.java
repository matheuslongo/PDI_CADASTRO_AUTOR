package com.pdiSpring.cadastroAutor.controller.dto;

import com.pdiSpring.cadastroAutor.model.Autor;
import com.pdiSpring.cadastroAutor.model.Livro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.time.LocalDate;
import java.util.UUID;

public record ResultadoGetLivroDTO(
    UUID id,
    String isbn,
    String titulo,
    LocalDate dtPublicacao,
    String genero,
    Double preco,
    AutorDTO autorDTO) {

}
