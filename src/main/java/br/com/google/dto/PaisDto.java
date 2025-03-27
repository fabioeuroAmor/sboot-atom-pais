package br.com.google.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaisDto {


    private Integer idPais;


    private String codigo;


    private String continente;


    private String nome;

}
