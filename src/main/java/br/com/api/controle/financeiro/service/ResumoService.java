package br.com.api.controle.financeiro.service;

import br.com.api.controle.financeiro.dto.ResumoDto;
import br.com.api.controle.financeiro.dto.ValorDespesaPorCategoria;
import br.com.api.controle.financeiro.model.DespesaModel;
import br.com.api.controle.financeiro.model.ReceitaModel;
import br.com.api.controle.financeiro.repository.DespesaRepository;
import br.com.api.controle.financeiro.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ResumoService {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private DespesaRepository despesaRepository;

    public BigDecimal calculaValorReceitasMes(int ano, int mes){
        var listaReceitas = receitaRepository.findByListaReceitasPorAnoMes(ano, mes);

        return listaReceitas
                .stream()
                .map(ReceitaModel::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculaValorDespesasMes(int ano, int mes){
        var listaReceitas = despesaRepository.findByListaDespesasPorAnoMes(ano, mes);

        return listaReceitas
                .stream()
                .map(DespesaModel::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal saldoFinalDoMes(BigDecimal valorReceitasMes, BigDecimal valorDespesasMes){

        return valorReceitasMes.subtract(valorDespesasMes);
    }

    public List<ValorDespesaPorCategoria> valorDespesaPorCategorias(int ano, int mes){
        List<Object[]> gastoPorCategoria = despesaRepository.valorTotalGastoPorCategoria(ano, mes);

        List<ValorDespesaPorCategoria> categorias = new ArrayList<>();

        gastoPorCategoria.forEach(linha -> {
            String categoria = (String) linha[0];
            BigDecimal valor = (BigDecimal) linha[1];

            ValorDespesaPorCategoria despesaPorCategoria = new ValorDespesaPorCategoria(categoria, valor);
            categorias.add(despesaPorCategoria);
        });

        return categorias;
    }

    public ResumoDto resumoMes(int ano, int mes){

        var calculoValorReceitas = calculaValorReceitasMes(ano, mes);
        var calculoValorDespesas = calculaValorDespesasMes(ano, mes);
        var saldoFinalMes = saldoFinalDoMes(calculoValorReceitas, calculoValorDespesas);
        List<ValorDespesaPorCategoria> despesaPorCategorias = valorDespesaPorCategorias(ano, mes);

        return new ResumoDto(calculoValorReceitas, calculoValorDespesas, saldoFinalMes, despesaPorCategorias);
    }
}
