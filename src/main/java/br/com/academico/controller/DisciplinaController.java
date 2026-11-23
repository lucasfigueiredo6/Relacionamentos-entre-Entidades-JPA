package br.com.academico.controller;

import br.com.academico.model.*;
import br.com.academico.repository.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/disciplinas")
public class DisciplinaController {
    private final DisciplinaRepository disciplinas;
    private final CursoRepository cursos;
    public DisciplinaController(DisciplinaRepository disciplinas, CursoRepository cursos) {
        this.disciplinas = disciplinas; this.cursos = cursos;
    }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Disciplina criar(@Valid @RequestBody Map<String, Object> dados) {
        Disciplina d = new Disciplina();
        d.setNome((String) dados.get("nome"));
        d.setCargaHoraria(dados.get("cargaHoraria") == null ? null : Integer.valueOf(dados.get("cargaHoraria").toString()));
        Long cursoId = Long.valueOf(dados.get("cursoId").toString());
        d.setCurso(cursos.findById(cursoId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado")));
        return disciplinas.save(d);
    }
    @GetMapping public List<Disciplina> listar() { return disciplinas.findAll(); }
    @GetMapping("/{id}") public Disciplina buscar(@PathVariable Long id) { return disciplinas.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada")); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) { disciplinas.delete(buscar(id)); }
}
