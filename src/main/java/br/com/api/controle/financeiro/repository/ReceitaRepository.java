package br.com.api.controle.financeiro.repository;

import br.com.api.controle.financeiro.model.ReceitaModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ReceitaRepository extends JpaRepository<ReceitaModel, Long> {

    @Query("""
            SELECT COUNT(r) > 0
            FROM ReceitaModel r
            WHERE r.descricao = :descricao\s
              AND r.data = :data
              AND FUNCTION('MONTH', r.data) = FUNCTION('MONTH', :data)""")
    Boolean findByReceitaPorDescricaoData(String descricao, LocalDate data);

    @Query("SELECT r FROM ReceitaModel r WHERE r.descricao = :descricao")
    List<ReceitaModel> findByDescricao(@Param("descricao") String descricao, Pageable pageable);


    @Query("""
            SELECT r FROM ReceitaModel
            r WHERE YEAR(r.data) = :ano
            AND MONTH(r.data) = :mes""")
    List<ReceitaModel> findByListaReceitasPorAnoMes(@Param("ano") int ano, @Param("mes") int mes, Pageable pageable);

    @Query("""
            SELECT r FROM ReceitaModel
            r WHERE YEAR(r.data) = :ano
            AND MONTH(r.data) = :mes""")
    List<ReceitaModel> findByListaReceitasPorAnoMes(@Param("ano") int ano, @Param("mes") int mes);

    //BigDecimal valorTotalReceitasMes(@Param("ano") int ano, @Param("mes") int mes);
}
