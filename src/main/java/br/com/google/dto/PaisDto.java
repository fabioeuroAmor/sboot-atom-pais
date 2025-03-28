package br.com.google.dto;

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


        private String nome;


        private String codigo;


        private String continente;


    }
