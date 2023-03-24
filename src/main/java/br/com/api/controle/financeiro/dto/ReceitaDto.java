package br.com.api.controle.financeiro.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ReceitaDto {

    private Long id;

    private String descricao;

    private BigDecimal valor;

    private LocalDate data;
}
