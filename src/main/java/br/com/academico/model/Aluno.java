package br.com.academico.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "alunos")
public class Aluno {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String nome;
    @Email @NotBlank @Column(unique = true) private String email;

    @ManyToMany
    @JoinTable(name = "aluno_disciplina",
        joinColumns = @JoinColumn(name = "aluno_id"),
        inverseJoinColumns = @JoinColumn(name = "disciplina_id"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"aluno_id", "disciplina_id"}))
    private Set<Disciplina> disciplinas = new HashSet<>();

    public Aluno() {}
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Set<Disciplina> getDisciplinas() { return disciplinas; }
    public void matricular(Disciplina disciplina) { disciplinas.add(disciplina); }
    public void cancelarMatricula(Disciplina disciplina) { disciplinas.remove(disciplina); }
}
