package br.com.pamcary.aluno.controller;

import br.com.pamcary.aluno.entity.Aluno;
import br.com.pamcary.aluno.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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

    @PostMapping
    public ResponseEntity criarAluno(@Valid @RequestBody Aluno aluno) {

        final Aluno alunoSalvo = alunoService.salvarAluno(aluno);

        URI location = getUri(alunoSalvo);

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/[id}")
    public ResponseEntity atualizarAluno(@PathVariable Long id, @Valid @RequestBody Aluno aluno) {

        final Aluno alunoAtualizado = alunoService.atualizarAluno(id, aluno);

        return ResponseEntity.ok(alunoAtualizado);
    }

    @DeleteMapping
    public ResponseEntity deletarAluno(@PathVariable Long id) {

        alunoService.removerAluno(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }


    private URI getUri(Aluno aluno) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(aluno.getId()).toUri();
    }
}
