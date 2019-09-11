package br.com.pamcary.aluno.controller;

import br.com.pamcary.aluno.entity.Aluno;
import br.com.pamcary.aluno.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping()
    public ResponseEntity<List<Aluno>> buscarTodos() {

        final List<Aluno> alunos = alunoService.buscarTodosAlunos();

        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/[id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {

        final Aluno aluno = alunoService.buscarAlunoPorId(id);

        return ResponseEntity.ok(aluno);
    }
}
