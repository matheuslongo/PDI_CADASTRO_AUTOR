package com.pdiSpring.cadastroAutor.controller.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErroResponseDTO(int status, String mensagem, List<ErroCampoDTO>erros) {

    public static ErroResponseDTO errorBadRequest(String mensagem){
        return  new ErroResponseDTO(HttpStatus.BAD_REQUEST.value(), mensagem, List.of());
    }

    public static ErroResponseDTO errorConflito(String mensagem){
        return  new ErroResponseDTO(HttpStatus.CONFLICT.value(), mensagem, List.of());
    }
}
