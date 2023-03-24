package br.com.api.controle.financeiro.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "receitas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReceitaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "A descrição não pode ser nula ou vázia")
    private String descricao;

    @NotNull
    @Positive
    private BigDecimal valor;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull
    private LocalDate data;
}
