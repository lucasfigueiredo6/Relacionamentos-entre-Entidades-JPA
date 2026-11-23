package br.com.academico.controller;

import br.com.academico.model.*;
import br.com.academico.repository.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/professores")
public class ProfessorController {
    private final ProfessorRepository professores;
    private final PerfilProfessorRepository perfis;
    public ProfessorController(ProfessorRepository professores, PerfilProfessorRepository perfis) {
        this.professores = professores; this.perfis = perfis;
    }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Professor criar(@Valid @RequestBody Professor professor) { return professores.save(professor); }
    @GetMapping public List<Professor> listar() { return professores.findAll(); }
    @GetMapping("/{id}") public Professor buscar(@PathVariable Long id) { return professores.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado")); }
    @PutMapping("/{id}") public Professor atualizar(@PathVariable Long id, @Valid @RequestBody Professor dados) {
        Professor p = buscar(id); p.setNome(dados.getNome()); p.setEmail(dados.getEmail()); return professores.save(p);
    }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) { professores.delete(buscar(id)); }

    @PostMapping("/{id}/perfil") @ResponseStatus(HttpStatus.CREATED)
    public PerfilProfessor criarPerfil(@PathVariable Long id, @Valid @RequestBody PerfilProfessor perfil) {
        Professor professor = buscar(id);
        if (perfis.findAll().stream().anyMatch(p -> p.getProfessor() != null && p.getProfessor().getId().equals(id)))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este professor já possui perfil");
        perfil.setProfessor(professor);
        return perfis.save(perfil);
    }
    @GetMapping("/{id}/perfil")
    public PerfilProfessor buscarPerfil(@PathVariable Long id) {
        buscar(id);
        return perfis.findAll().stream().filter(p -> p.getProfessor() != null && p.getProfessor().getId().equals(id)).findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Perfil não encontrado"));
    }
}
