package br.com.academico.repository;
import br.com.academico.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AlunoRepository extends JpaRepository<Aluno, Long> {}
