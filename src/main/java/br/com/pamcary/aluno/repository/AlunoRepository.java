package br.com.pamcary.aluno.repository;

import br.com.pamcary.aluno.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {


	
}