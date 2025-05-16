package br.com.google.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaisDto {

    @NotBlank(message = "O id do país é obrigatório.")
    private Integer idPais;

    @NotBlank(message = "O código do país é obrigatório.")
    private String codigo;


    @NotBlank(message = "O continente do país é obrigatório.")
    private String continente;

    @NotBlank(message = "O nome do país é obrigatório.")
    private String nome;

}
