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
@RequestMapping("/api/alunos")
public class AlunoController {
    private final AlunoRepository alunos;
    private final DisciplinaRepository disciplinas;
    public AlunoController(AlunoRepository alunos, DisciplinaRepository disciplinas) {
        this.alunos = alunos; this.disciplinas = disciplinas;
    }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Aluno criar(@Valid @RequestBody Aluno aluno) { return alunos.save(aluno); }
    @GetMapping public List<Aluno> listar() { return alunos.findAll(); }
    @GetMapping("/{id}") public Aluno buscar(@PathVariable Long id) { return alunos.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado")); }
    @PutMapping("/{id}") public Aluno atualizar(@PathVariable Long id, @Valid @RequestBody Aluno dados) {
        Aluno a = buscar(id); a.setNome(dados.getNome()); a.setEmail(dados.getEmail()); return alunos.save(a);
    }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) { alunos.delete(buscar(id)); }

    @PostMapping("/{alunoId}/disciplinas") @ResponseStatus(HttpStatus.CREATED)
    public Aluno matricular(@PathVariable Long alunoId, @RequestBody Map<String, Long> dados) {
        Aluno aluno = buscar(alunoId);
        Long disciplinaId = dados.get("disciplinaId");
        Disciplina disciplina = disciplinas.findById(disciplinaId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada"));
        aluno.matricular(disciplina);
        return alunos.save(aluno);
    }
    @DeleteMapping("/{alunoId}/disciplinas/{disciplinaId}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable Long alunoId, @PathVariable Long disciplinaId) {
        Aluno aluno = buscar(alunoId);
        Disciplina disciplina = disciplinas.findById(disciplinaId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada"));
        aluno.cancelarMatricula(disciplina);
        alunos.save(aluno);
    }
}
