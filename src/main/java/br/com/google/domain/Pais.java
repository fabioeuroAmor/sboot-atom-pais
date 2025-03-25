package br.com.google.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="pais")
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@SequenceGenerator(name="tbl_pais_id_pais_seq", sequenceName="tbl_pais_id_pais_seq",initialValue=1, allocationSize=1)
public class Pais {


    private Integer idPais;

    private String nome;

    private String codigo;

    private String continente;
}
