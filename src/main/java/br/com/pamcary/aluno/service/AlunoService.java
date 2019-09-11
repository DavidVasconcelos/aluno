package br.com.pamcary.aluno.service;

import br.com.pamcary.aluno.entity.Aluno;
import br.com.pamcary.aluno.exception.AlunoInexistenteException;
import br.com.pamcary.aluno.exception.IdNuloException;
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

    public void salvarAluno(final Aluno aluno) {

        alunoRepository.save(aluno);

    }

    public void atualizarAluno(final Aluno aluno) {

        Optional.ofNullable(aluno.getId()).orElseThrow(() -> new IdNuloException("Aluno com Id nulo"));

        final Aluno alunoSalvo = buscarAlunoPorId(aluno.getId());

        BeanUtils.copyProperties(aluno, alunoSalvo, "id");

        alunoRepository.save(aluno);

    }

    public void removerAluno(final Aluno aluno) {

        final Aluno alunoSalvo = buscarAlunoPorId(aluno.getId());

        alunoRepository.delete(alunoSalvo);
    }
}
