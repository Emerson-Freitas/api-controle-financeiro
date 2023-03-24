package br.com.api.controle.financeiro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ResumoDto {

    /*public ResumoDto(BigDecimal valorTotalReceitaMes, BigDecimal valorTotalDespesaMes, BigDecimal saldoFinal,
                     List<ValorDespesaPorCategoria> valorDespesaPorCategoria){
        this.valorTotalReceitaMes = valorTotalReceitaMes;
        this.valorTotalDespesaMes = valorTotalDespesaMes;
        this.saldoFinal = saldoFinal;
        this.valorDespesaPorCategoria = valorDespesaPorCategoria;
    }*/

    private BigDecimal valorTotalReceitaMes;

    private BigDecimal valorTotalDespesaMes;

    private BigDecimal saldoFinal;

    private List<ValorDespesaPorCategoria> valorDespesaPorCategoria;
}
