package com.pdiSpring.cadastroAutor.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "autor", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "listLivro")
@EntityListeners(AuditingEntityListener.class)
public class Autor {

    @Id
    @Column(name = "idautor")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", length = 100)
    private String nome;

    @Column(name = "dtnascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade", length = 50, nullable = false)
    private String nacionalidade;

    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY)
    private List<Livro> listLivro;

    @LastModifiedDate
    @Column(name = "dataAtulizacao")
    private LocalDateTime dataAtulizacao;

    @CreatedDate
    @Column(name = "dataCriacao")
    private LocalDateTime dataCriacao;


    public Autor(UUID id) {
        this.id = id;
    }
}
