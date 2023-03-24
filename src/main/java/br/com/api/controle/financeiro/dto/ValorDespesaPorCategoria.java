package br.com.api.controle.financeiro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ValorDespesaPorCategoria {

    private String categoria;

    private BigDecimal valor;

}
