package br.com.pamcary.aluno.service;

import br.com.pamcary.aluno.entity.Aluno;
import br.com.pamcary.aluno.exception.AlunoInexistenteException;
import br.com.pamcary.aluno.repository.AlunoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public List<Aluno> buscarTodosAlunos() {

        return alunoRepository.findAll();
    }

    public Aluno buscarAlunoPorId(final Long id) {

        final Optional<Aluno> optionalAluno = alunoRepository.findById(id);

        final Aluno aluno = optionalAluno.orElseThrow(() -> new AlunoInexistenteException("Aluno não encontrado"));

        return aluno;

    }

    public Aluno salvarAluno(final Aluno aluno) {

        return alunoRepository.save(aluno);

    }

    public Aluno atualizarAluno(final Long id, final Aluno aluno) {

        final Aluno alunoSalvo = buscarAlunoPorId(id);

        BeanUtils.copyProperties(aluno, alunoSalvo, "id");

        return alunoRepository.save(alunoSalvo);

    }

    public void removerAluno(final Long id) {

        final Aluno alunoSalvo = buscarAlunoPorId(id);

        alunoRepository.delete(alunoSalvo);
    }
}
