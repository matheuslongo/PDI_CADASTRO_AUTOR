package com.pdiSpring.cadastroAutor.controller.dto;

import com.pdiSpring.cadastroAutor.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,
        @NotBlank(message = "Campo obrigatório com no maximo 290 caracteres")
        String nome,
        @NotNull(message = "Campo obrigatório! TESTE")
        LocalDate dtNascimento,
        @NotBlank(message = "Campo obrigatório")
        String nacionalidade) {


    public Autor dtoToAutor(){
        Autor autor = new Autor();
        autor.setDataNascimento(this.dtNascimento);
        autor.setNome(this.nome);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }

}
