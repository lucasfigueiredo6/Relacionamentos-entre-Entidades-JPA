package br.com.academico.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "professores")
public class Professor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String nome;
    @Email @NotBlank @Column(unique = true) private String email;

    @OneToOne(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private PerfilProfessor perfil;

    public Professor() {}
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public PerfilProfessor getPerfil() { return perfil; }
    public void setPerfil(PerfilProfessor perfil) {
        this.perfil = perfil;
        if (perfil != null) perfil.setProfessor(this);
    }
}
