package br.com.google.repository;

import br.com.google.domain.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Integer> {
    List<Pais> findByNomeContaining(String nome);
    List<Pais> findByContinente(String continente);

    // Método para encontrar por código
    Pais findByCodigo(String codigo);

    //SELECT p FROM Pais p WHERE p.nome = :nome
    @Query("SELECT p FROM Pais p WHERE p.nome = :nome")
    Pais buscarPaisNome(@Param("nome") String nome);

    @Query(value = "SELECT * FROM pais p WHERE p.nome = :nome", nativeQuery = true)
    Pais buscarPaisNomeQueryNativa(@Param("nome") String nome);

}
