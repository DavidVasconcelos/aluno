package br.com.pamcary.aluno.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "ID gerado automaticamente")
    private Long id;

    @NotEmpty(message = "Nome é obrigatório")
    @ApiModelProperty( notes = "Nome do aluno", required = true)
    private String nome;

    @NotNull(message = "Idade é obrigatória")
    @Min(value = 1, message = "Idade não pode ser zero")
    @ApiModelProperty( notes = "Idade do aluno", required = true)
    private Integer idade;


}
