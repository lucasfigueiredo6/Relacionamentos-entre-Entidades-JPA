package br.com.academico.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "perfis_professor")
public class PerfilProfessor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String especializacao;
    private String telefone;
    @Column(length = 2000) private String curriculo;

    @OneToOne(optional = false)
    @JoinColumn(name = "professor_id", nullable = false, unique = true)
    @JsonIgnore
    private Professor professor;

    public PerfilProfessor() {}
    public Long getId() { return id; }
    public String getEspecializacao() { return especializacao; }
    public void setEspecializacao(String especializacao) { this.especializacao = especializacao; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getCurriculo() { return curriculo; }
    public void setCurriculo(String curriculo) { this.curriculo = curriculo; }
    public Professor getProfessor() { return professor; }
    public void setProfessor(Professor professor) { this.professor = professor; }
}
