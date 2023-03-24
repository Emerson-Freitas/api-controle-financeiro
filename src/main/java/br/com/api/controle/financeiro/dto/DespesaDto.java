package br.com.api.controle.financeiro.dto;

import br.com.api.controle.financeiro.model.CategoriaDespesa;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class DespesaDto {

    private Long id;

    private String descricao;

    private BigDecimal valor;

    private LocalDate data;

    private CategoriaDespesa categoriaDespesa;

}
