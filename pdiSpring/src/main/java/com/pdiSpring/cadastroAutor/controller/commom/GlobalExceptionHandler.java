package com.pdiSpring.cadastroAutor.controller.commom;

import com.pdiSpring.cadastroAutor.controller.dto.ErroCampoDTO;
import com.pdiSpring.cadastroAutor.controller.dto.ErroResponseDTO;
import com.pdiSpring.cadastroAutor.exceptions.OperacaoNaoPermitidaException;
import com.pdiSpring.cadastroAutor.exceptions.RegistroDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResponseDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampoDTO> erroCampoDTOList = fieldErrors.stream()
                .map(fieldError -> new ErroCampoDTO(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ErroResponseDTO(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", erroCampoDTOList);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    public ErroResponseDTO handleRegistroDuplicadoException(RegistroDuplicadoException e){
        return ErroResponseDTO.errorConflito(e.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResponseDTO handleOperacaoNaoPermitidaException(OperacaoNaoPermitidaException e){
        return ErroResponseDTO.errorConflito(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResponseDTO handleErroNaoTratado(RuntimeException e){
        return new ErroResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocorreu um erro inesperado entre em contato com o a administração", List.of());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResponseDTO handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        return new ErroResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Tipo de metodo usado na requisicao invalido: " + e.getMethod(), List.of());
    }

}
