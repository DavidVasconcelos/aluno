package br.com.pamcary.aluno.controller;

import br.com.pamcary.aluno.entity.Aluno;
import br.com.pamcary.aluno.exception.AlunoInexistenteException;
import br.com.pamcary.aluno.exception.ErrorResponse;
import br.com.pamcary.aluno.service.AlunoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AlunoController.class)
public class AlunoControllerTest {

    private ObjectMapper mapper;

    private Gson gson;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlunoService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        gson = new Gson();
    }

    @Test
    public void deveTrazerUmaListaDeAlunos() throws Exception {

        //cenario
        final Aluno aluno = new Aluno(1L, "Aluno 1", 15);
        final Aluno aluno2 = new Aluno(2L, "Aluno 2", 16);

        final List<Aluno> alunos = Arrays.asList(aluno, aluno2);

        final String alunosJson = gson.toJson(alunos);

        when(this.service.buscarTodosAlunos()).thenReturn(alunos);

        //acao
        mockMvc.perform(MockMvcRequestBuilders.get("/aluno"))
                //verificao
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(alunosJson))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void deveTrazerUmAlunoPeloId() throws Exception {

        //cenario
        final Aluno aluno = new Aluno(1L, "Aluno 1", 15);
        final Long id = 1L;

        final String alunoJson = mapper.writeValueAsString(aluno);

        when(this.service.buscarAlunoPorId(id)).thenReturn(aluno);

        //acao
        mockMvc.perform(MockMvcRequestBuilders.get("/aluno/{id}", id))
                //verificao
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(alunoJson))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void deveTratarAlunoInexistente() throws Exception {

        //cenario
        final Long id = 1L;

        final ErrorResponse errorResponse = new ErrorResponse();
        final List<String> detalhes = Arrays.asList("Aluno não encontrado");
        errorResponse.setDetalhes(detalhes);
        errorResponse.setMensagem("Conflito");

        final String errorJson = mapper.writeValueAsString(errorResponse);

        when(this.service.buscarAlunoPorId(id)).thenThrow(new AlunoInexistenteException("Aluno não encontrado"));

        //acao
        mockMvc.perform(MockMvcRequestBuilders.get("/aluno/{id}", id))
                //verificacao
                .andExpect(status().isConflict())
                .andExpect(MockMvcResultMatchers.content().json(errorJson))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8));
        ;
    }


    @Test
    public void deveTratarNomeNulo() throws Exception {

        //cenario
        final Aluno aluno = new Aluno(null, "", 1);

        final String alunoJson = mapper.writeValueAsString(aluno);

        final ErrorResponse errorResponse = new ErrorResponse();
        final List<String> detalhes = Arrays.asList("Nome é obrigatório");
        errorResponse.setDetalhes(detalhes);
        errorResponse.setMensagem("Falha na validação");

        final String errorJson = mapper.writeValueAsString(errorResponse);

        //acao
        mockMvc.perform(MockMvcRequestBuilders.post("/aluno")
                .content(alunoJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //verificacao
                .andExpect(status().isConflict())
                .andExpect(MockMvcResultMatchers.content().json(errorJson))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void deveTratarIdadeNula() throws Exception {

        //cenario
        final Aluno aluno = new Aluno(null, "Teste", null);

        final String alunoJson = mapper.writeValueAsString(aluno);

        final ErrorResponse errorResponse = new ErrorResponse();
        final List<String> detalhes = Arrays.asList("Idade é obrigatória");
        errorResponse.setDetalhes(detalhes);
        errorResponse.setMensagem("Falha na validação");

        final String errorJson = mapper.writeValueAsString(errorResponse);

        //acao
        mockMvc.perform(MockMvcRequestBuilders.post("/aluno")
                .content(alunoJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //verificacao
                .andExpect(status().isConflict())
                .andExpect(MockMvcResultMatchers.content().json(errorJson))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void deveTratarIdadeComValorZero() throws Exception {

        //cenario
        final Aluno aluno = new Aluno(null, "Teste", 0);

        final String alunoJson = mapper.writeValueAsString(aluno);

        final ErrorResponse errorResponse = new ErrorResponse();
        final List<String> detalhes = Arrays.asList("Idade não pode ser zero");
        errorResponse.setDetalhes(detalhes);
        errorResponse.setMensagem("Falha na validação");

        final String errorJson = mapper.writeValueAsString(errorResponse);

        //acao
        mockMvc.perform(MockMvcRequestBuilders.post("/aluno")
                .content(alunoJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //verificacao
                .andExpect(status().isConflict())
                .andExpect(MockMvcResultMatchers.content().json(errorJson))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void deveTratarTodosCamposNulos() throws Exception {

        //cenario
        final Aluno aluno = new Aluno(null, "", null);

        final String alunoJson = mapper.writeValueAsString(aluno);

        final ErrorResponse errorResponse = new ErrorResponse();
        final List<String> detalhes = Arrays.asList("Idade é obrigatória", "Nome é obrigatório");
        errorResponse.setDetalhes(detalhes);
        errorResponse.setMensagem("Falha na validação");

        final String errorJson = mapper.writeValueAsString(errorResponse);

        //acao
        mockMvc.perform(MockMvcRequestBuilders.post("/aluno")
                .content(alunoJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //verificacao
                .andExpect(status().isConflict())
                .andExpect(MockMvcResultMatchers.content().json(errorJson))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8));
    }


    @Test
    public void deveRetornarStatusCreated() throws Exception {

        //cenario
        final Aluno aluno = new Aluno(1L, "Teste", 15);

        final String alunoJson = mapper.writeValueAsString(aluno);

        when(this.service.salvarAluno(aluno)).thenReturn(aluno);

        //acao
        mockMvc.perform(MockMvcRequestBuilders.post("/aluno")
                .contentType(MediaType.APPLICATION_JSON)
                .content(alunoJson)
                .characterEncoding("utf-8"))
                //verificao
                .andExpect(status().isCreated());
    }

    @Test
    public void deveRetornarStatusOKComAlunoAlterado() throws Exception {

        //cenario
        final Aluno aluno = new Aluno(1L, "Teste", 15);
        final Long id = 1L;

        final String alunoJson = mapper.writeValueAsString(aluno);

        when(this.service.atualizarAluno(id, aluno)).thenReturn(aluno);

        //acao
        mockMvc.perform(MockMvcRequestBuilders.put("/aluno/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(alunoJson)
                .characterEncoding("utf-8"))
                //verificao
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(alunoJson))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void deveRetornarStatusOK() throws Exception {

        //cenario
        final Long id = 1L;

        doNothing().when(service).removerAluno(id);

        //acao
        mockMvc.perform(MockMvcRequestBuilders.delete("/aluno/{id}", id))
                //verificao
                .andExpect(status().isOk());
    }


}
