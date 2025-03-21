package com.pdiSpring.cadastroAutor.controller;

import com.pdiSpring.cadastroAutor.controller.dto.AutorDTO;
import com.pdiSpring.cadastroAutor.model.Autor;
import com.pdiSpring.cadastroAutor.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autor")
@RequiredArgsConstructor
public class AutorController implements GenericController {


    private final AutorService autorService;

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @Valid AutorDTO autorDTO) {
        Autor autor = autorDTO.dtoToAutor();
        autorService.save(autor);
        URI location = gerarHeaderLocation(autor.getId());
        return ResponseEntity.created(location).build();


    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> getAutorById(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        return autorService.findById(idAutor).map(autor -> {
            AutorDTO dto = new AutorDTO(
                    autor.getId(),
                    autor.getNome(),
                    autor.getDataNascimento(),
                    autor.getNacionalidade());
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAutorById(@PathVariable("id") String id) {

        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.findById(idAutor);
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        autorService.deleteByAutor(autorOptional.get());
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> filtroByData(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {

        List<Autor> autorList = autorService.findByExample(nome, nacionalidade);
        List<AutorDTO> listaAutorDTO = autorList.stream()
                .map(autor -> new AutorDTO(
                        autor.getId(),
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade())).collect(Collectors.toList());
        return ResponseEntity.ok(listaAutorDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") String id, @RequestBody @Valid AutorDTO autorDTO) {

        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.findById(idAutor);
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Autor autor = autorOptional.get();
        autor.setNome(autorDTO.nome());
        autor.setDataNascimento(autorDTO.dtNascimento());
        autor.setNacionalidade(autorDTO.nacionalidade());
        autorService.atualizar(autor);
        return ResponseEntity.noContent().build();

    }
}
