package com.pdiSpring.cadastroAutor.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "livro", schema = "public")
public class Livro {

    @Id
    @Column(name = "idlivro")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn", length = 150, nullable = false)
    private String isbn;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "datapuplicacao")
    private LocalDate dataPublicacao;

    @Column(name = "genero", length = 30, nullable = false)
    private String genero;

    @Column(name = "preco")
    private Double preco;

    @ManyToOne(/*cascade = CascadeType.ALL*/ fetch = FetchType.EAGER)
    @JoinColumn(name = "idautor")
    private Autor autor;


}
