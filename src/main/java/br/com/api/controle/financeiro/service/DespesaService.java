package br.com.api.controle.financeiro.service;

import br.com.api.controle.financeiro.dto.DespesaDto;
import br.com.api.controle.financeiro.exception.DespesaDuplicadaException;
import br.com.api.controle.financeiro.model.CategoriaDespesa;
import br.com.api.controle.financeiro.model.DespesaModel;
import br.com.api.controle.financeiro.repository.DespesaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.stream.Collectors;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public DespesaDto cadastrarDespesa(DespesaDto dto) throws DespesaDuplicadaException {
        DespesaModel despesa = modelMapper.map(dto, DespesaModel.class);
        Boolean existeDespesa = despesaRepository
                .pesquisaDespesaPorDescricaoData(despesa.getDescricao(), despesa.getData());

        if(existeDespesa){
            throw new DespesaDuplicadaException("A despesa com a descrição " + despesa.getDescricao()
                    + " já foi cadastrada nesse mês! " + "(data em que a despesa foi cadastrada: " + despesa.getData() + ")");
        }

        if(despesa.getCategoriaDespesa() == null){
            despesa.setCategoriaDespesa(CategoriaDespesa.OUTRAS);
        }
        despesaRepository.save(despesa);

        return modelMapper.map(despesa, DespesaDto.class);
    }

    public DespesaDto obterDespesaPorId(Long id){
       DespesaModel despesa = despesaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Despesa não encontrada"));

       return modelMapper.map(despesa, DespesaDto.class);
    }

    public Page<DespesaDto> obterDespesas(Pageable pageable){
        return despesaRepository.findAll(pageable)
                .map(d -> modelMapper.map(d, DespesaDto.class));
    }

    public Page<DespesaDto> obterReceitasPorDescricao(String descricao, Pageable pageable){
        var lista = despesaRepository.findByDescricao(descricao, pageable)
                .stream()
                .map(d -> modelMapper.map(d, DespesaDto.class))
                .collect(Collectors.toList());

        return new PageImpl<>(lista, pageable, lista.size());
    }

    public Page<DespesaDto> listaReceitasPorAnoMes(int ano, int mes, Pageable pageable){
        YearMonth data = YearMonth.of(ano, mes);
        var dataMes = data.getMonth().getValue();

        var listaDespesas = despesaRepository.findByListaDespesasPorAnoMes(data.getYear(), dataMes, pageable)
                .stream().map(d -> modelMapper.map(d, DespesaDto.class))
                .collect(Collectors.toList());

        return new PageImpl<>(listaDespesas, pageable, listaDespesas.size());

    }

    public DespesaDto atualizarDespesa(DespesaDto dto){
        DespesaModel despesa = despesaRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Despesa não encontrada"));

        modelMapper.map(dto, despesa);
        despesaRepository.save(despesa);

        return modelMapper.map(despesa, DespesaDto.class);
    }

    public void excluirDespesa(Long id){
        despesaRepository.deleteById(id);
    }
}
