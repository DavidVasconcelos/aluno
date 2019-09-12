package br.com.pamcary.aluno.configuration;

import br.com.pamcary.aluno.exception.AlunoInexistenteException;
import br.com.pamcary.aluno.exception.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> detalhes = new ArrayList<>();
        detalhes.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Erro interno no servidor", detalhes);
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AlunoInexistenteException.class)
    public final ResponseEntity<Object> handleAlunoInexistenteException(AlunoInexistenteException ex, WebRequest request) {
        List<String> detalhes = new ArrayList<>();
        detalhes.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Conflito", detalhes);
        return new ResponseEntity(error, HttpStatus.CONFLICT);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> detalhes = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            detalhes.add(error.getDefaultMessage());
        }
        ErrorResponse error = new ErrorResponse("Falha na validação", detalhes);
        return new ResponseEntity(error, HttpStatus.CONFLICT);
    }






}