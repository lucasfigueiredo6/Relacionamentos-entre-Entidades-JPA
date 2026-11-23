package br.com.academico.controller;

import br.com.academico.model.Curso;
import br.com.academico.repository.CursoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {
    private final CursoRepository repository;
    public CursoController(CursoRepository repository) { this.repository = repository; }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Curso criar(@Valid @RequestBody Curso curso) { return repository.save(curso); }
    @GetMapping public List<Curso> listar() { return repository.findAll(); }
    @GetMapping("/{id}") public Curso buscar(@PathVariable Long id) { return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado")); }
    @PutMapping("/{id}") public Curso atualizar(@PathVariable Long id, @Valid @RequestBody Curso dados) {
        Curso c = buscar(id); c.setNome(dados.getNome()); c.setDescricao(dados.getDescricao()); return repository.save(c);
    }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) { repository.delete(buscar(id)); }
}
