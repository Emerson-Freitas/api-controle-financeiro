package br.com.api.controle.financeiro.repository;

import br.com.api.controle.financeiro.model.DespesaModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface DespesaRepository extends JpaRepository<DespesaModel, Long> {

    @Query("""
            SELECT COUNT(d) > 0
            FROM DespesaModel d
            WHERE d.descricao = :descricao\s
              AND d.data = :data
              AND FUNCTION('MONTH', d.data) = FUNCTION('MONTH', :data)""")
    Boolean pesquisaDespesaPorDescricaoData(String descricao, LocalDate data);

    @Query("SELECT d FROM DespesaModel d WHERE d.descricao = :descricao")
    List<DespesaModel> findByDescricao(@Param("descricao") String descricao, Pageable pageable);

    @Query("""
            SELECT d FROM DespesaModel
            d WHERE YEAR(d.data) = :ano
            AND MONTH(d.data) = :mes""")
    List<DespesaModel> findByListaDespesasPorAnoMes(@Param("ano") int ano, @Param("mes") int mes, Pageable pageable);

    @Query("""
            SELECT d FROM DespesaModel
            d WHERE YEAR(d.data) = :ano
            AND MONTH(d.data) = :mes""")
    List<DespesaModel> findByListaDespesasPorAnoMes(@Param("ano") int ano, @Param("mes") int mes);


    @Query(value = "SELECT categoria_despesa, SUM(valor) FROM despesas WHERE YEAR(data) = ?1 AND MONTH(data) = ?2 GROUP BY categoria_despesa", nativeQuery = true)
    Map<String, BigDecimal> valorTotalGastoPorCategoria(int ano, int mes);

}