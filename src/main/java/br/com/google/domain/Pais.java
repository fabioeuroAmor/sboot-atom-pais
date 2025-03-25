package br.com.google.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Getter
@Setter
@Entity
@Table(name="pais")
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@SequenceGenerator(name="pais_id_pais_seq", sequenceName="pais_id_pais_seq",initialValue=1, allocationSize=1)
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "pais_id_pais_seq")
    @Column(name="id_pais")
    private Integer idPais;

    @Column(name="nome")
    private String nome;

    @Column(name="codigo")
    private String codigo;

    @Column(name="continente")
    private String continente;


}
