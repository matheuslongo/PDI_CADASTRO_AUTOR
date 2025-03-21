package com.pdiSpring.cadastroAutor.controller;

import com.pdiSpring.cadastroAutor.controller.dto.AutorDTO;
import com.pdiSpring.cadastroAutor.controller.dto.CadastroLivroDTO;
import com.pdiSpring.cadastroAutor.controller.dto.ResultadoGetLivroDTO;
import com.pdiSpring.cadastroAutor.model.Livro;
import com.pdiSpring.cadastroAutor.service.AutorService;
import com.pdiSpring.cadastroAutor.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("livro")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService livroService;
    private final AutorService autorService;

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @Valid CadastroLivroDTO cadastroLivroDTO) {
        Livro livro = cadastroLivroDTO.dtoToLivro(cadastroLivroDTO);
        livroService.save(livro);
        URI uri = gerarHeaderLocation(livro.getId());

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getLivroById(@PathVariable("id") String uuid) {
        var idLivro = UUID.fromString(uuid);
        return livroService.findById(idLivro).map(livro -> {
            AutorDTO dtoAutor = new AutorDTO(livro.getAutor().getId(), livro.getAutor().getNome(), livro.getAutor().getDataNascimento(), livro.getAutor().getNacionalidade());
            ResultadoGetLivroDTO dto = new ResultadoGetLivroDTO(
                    livro.getId(),
                    livro.getIsbn(),
                    livro.getTitulo(),
                    livro.getDataPublicacao(),
                    livro.getGenero(),
                    livro.getPreco(),
                    dtoAutor);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteLivroById(@PathVariable("id") String id) {

        var idLivro = UUID.fromString(id);
        Optional<Livro> livroOptional = livroService.findById(idLivro);
        if (livroOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        livroService.deleteByLivro(livroOptional.get());
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<?> filtroLivro(
            @RequestParam(name = "isbn", required = false)
            String isbn,
            @RequestParam(name = "titulo", required = false)
            String titulo,
            @RequestParam(name = "genero", required = false)
            String genero,
            @RequestParam(name = "nomeAutor", required = false)
            String nomeAutor,
            @RequestParam(name = "anoPublicacao", required = false)
            Integer anoPublicacao
    ) {
        var resultado = livroService.filtroLivro(isbn, titulo, genero, nomeAutor, anoPublicacao);
        var lista = resultado.stream().map(livro -> {
            AutorDTO dtoAutor = new AutorDTO(livro.getAutor().getId(), livro.getAutor().getNome(), livro.getAutor().getDataNascimento(), livro.getAutor().getNacionalidade());
            ResultadoGetLivroDTO dto = new ResultadoGetLivroDTO(
                    livro.getId(),
                    livro.getIsbn(),
                    livro.getTitulo(),
                    livro.getDataPublicacao(),
                    livro.getGenero(),
                    livro.getPreco(),
                    dtoAutor);
            return ResponseEntity.ok(dto);
        }).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") String id, @RequestBody @Valid CadastroLivroDTO dtoLivro) {
        return livroService.findById(UUID.fromString(id)).map(livro -> {
            Livro livroAux = dtoLivro.dtoToLivro(dtoLivro);
            livro.setDataPublicacao(livroAux.getDataPublicacao());
            livro.setPreco(livroAux.getPreco());
            livro.setIsbn(livroAux.getIsbn());
            livro.setTitulo(livroAux.getTitulo());
            livro.setGenero(livroAux.getGenero());
            livro.setAutor(livroAux.getAutor());
            livroService.atualizar(livro);
            return ResponseEntity.ok(livro);
        }).orElseGet(() -> ResponseEntity.notFound().build());

    }

}
