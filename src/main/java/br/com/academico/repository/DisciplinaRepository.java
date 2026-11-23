package br.com.academico.repository;
import br.com.academico.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {}
