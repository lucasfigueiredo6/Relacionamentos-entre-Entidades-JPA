package br.com.academico.repository;
import br.com.academico.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProfessorRepository extends JpaRepository<Professor, Long> {}
