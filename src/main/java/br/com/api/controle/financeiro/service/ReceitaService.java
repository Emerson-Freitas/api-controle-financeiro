package br.com.api.controle.financeiro.service;

import br.com.api.controle.financeiro.dto.ReceitaDto;
import br.com.api.controle.financeiro.exception.ReceitaDuplicadaException;
import br.com.api.controle.financeiro.model.ReceitaModel;
import br.com.api.controle.financeiro.repository.ReceitaRepository;
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
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ReceitaDto cadastrar(ReceitaDto receitaDto) throws ReceitaDuplicadaException {
        ReceitaModel receita = modelMapper.map(receitaDto, ReceitaModel.class);
        Boolean possuiReceita = receitaRepository.findByReceitaPorDescricaoData(receita.getDescricao(), receita.getData());

        if(possuiReceita){
            throw new ReceitaDuplicadaException("A receita com a descrição " + receita.getDescricao()
                + " já foi cadastrada nesse mês! " + "(data em que a receita foi cadastrada: " + receita.getData() + ")");
        }
        receitaRepository.save(receita);

        return modelMapper.map(receita, ReceitaDto.class);
    }

    public Page<ReceitaDto> obterReceitas(Pageable pageable){
        return receitaRepository.findAll(pageable)
                .map(r -> modelMapper.map(r, ReceitaDto.class));
    }

    public ReceitaDto obterReceitaPorId(Long id){
        var receita = receitaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Receita não encontrada"));

        return modelMapper.map(receita, ReceitaDto.class);
    }

    public Page<ReceitaDto> obterReceitasPorDescricao(String descricao, Pageable pageable){
        var lista = receitaRepository.findByDescricao(descricao, pageable)
                .stream()
                .map(r -> modelMapper.map(r, ReceitaDto.class))
                .collect(Collectors.toList());

        return new PageImpl<>(lista, pageable, lista.size());
    }

    public Page<ReceitaDto> listaReceitasPorAnoMes(int ano, int mes, Pageable pageable){

        YearMonth data = YearMonth.of(ano, mes);
        var dataMes = data.getMonth().getValue();

        var listaReceitas = receitaRepository
                .findByListaReceitasPorAnoMes(data.getYear(), dataMes, pageable)
                .stream().map(r -> modelMapper.map(r, ReceitaDto.class))
                .collect(Collectors.toList());

        return new PageImpl<>(listaReceitas, pageable, listaReceitas.size());
    }

    public ReceitaDto atualizarReceita(ReceitaDto receitaDto){
        ReceitaModel receita = receitaRepository.findById(receitaDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Receita não encontrada"));

        modelMapper.map(receitaDto, receita);
        receitaRepository.save(receita);

        return modelMapper.map(receita, ReceitaDto.class);
    }

    public void excluirReceita(Long id){
        receitaRepository.deleteById(id);
    }

}
