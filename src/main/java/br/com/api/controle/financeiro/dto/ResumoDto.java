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
    
    private BigDecimal valorTotalReceitaMes;

    private BigDecimal valorTotalDespesaMes;

    private BigDecimal saldoFinal;

    private List<ValorDespesaPorCategoria> valorDespesaPorCategoria;
}
