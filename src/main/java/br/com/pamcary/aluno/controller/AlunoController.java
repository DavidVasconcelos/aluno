package br.com.pamcary.aluno.controller;

import br.com.pamcary.aluno.model.Aluno;
import br.com.pamcary.aluno.service.AlunoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/aluno")
@Api(value = "Aluno", description = "Aluno REST API")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @ApiOperation(httpMethod = "GET", value = "Método get para apresentar todos os alunos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna todos os alunos cadastrados", response = Aluno.class)})
    @GetMapping
    public ResponseEntity<List<Aluno>> buscarTodos() {

        final List<Aluno> alunos = alunoService.buscarTodosAlunos();

        return ResponseEntity.ok(alunos);
    }


    @ApiOperation(httpMethod = "GET", value = "Método get para pesquisar uma aluno por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o aluno pesquisado", response = Aluno.class),
            @ApiResponse(code = 409, message = "Informa que o aluno não existe", response = Aluno.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {

        final Aluno aluno = alunoService.buscarAlunoPorId(id);

        return ResponseEntity.ok(aluno);
    }

    @ApiOperation(httpMethod = "POST", value = "Método post inserir um aluno")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Retorna o path para pesquisa")})
    @PostMapping
    public ResponseEntity criarAluno(@Valid @RequestBody Aluno aluno) {

        final Aluno alunoSalvo = alunoService.salvarAluno(aluno);

        URI location = getUri(alunoSalvo);

        return ResponseEntity.created(location).build();

    }

    @ApiOperation(httpMethod = "PUT", value = "Método put para alterar um aluno por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso", response = Aluno.class)})
    @PutMapping("/{id}")
    public ResponseEntity atualizarAluno(@PathVariable Long id, @Valid @RequestBody Aluno aluno) {

        final Aluno alunoAtualizado = alunoService.atualizarAluno(id, aluno);

        return ResponseEntity.ok(alunoAtualizado);
    }

    @ApiOperation(httpMethod = "DELETE", value = "Método put para deletar um aluno por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso")})
    @DeleteMapping("/{id}")
    public ResponseEntity deletarAluno(@PathVariable Long id) {

        alunoService.removerAluno(id);

        return ResponseEntity.ok().build();
    }


    private URI getUri(Aluno aluno) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(aluno.getId()).toUri();
    }
}
