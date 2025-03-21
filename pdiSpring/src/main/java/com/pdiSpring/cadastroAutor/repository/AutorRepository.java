package com.pdiSpring.cadastroAutor.repository;

import com.pdiSpring.cadastroAutor.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AutorRepository extends JpaRepository<Autor, UUID> {

    @Override
    Optional<Autor> findById(UUID uuid);

   Optional<Autor> findByNomeAndDataNascimentoAndNacionalidade(String nome,LocalDate dataNascimento, String nacionalidade);

}
