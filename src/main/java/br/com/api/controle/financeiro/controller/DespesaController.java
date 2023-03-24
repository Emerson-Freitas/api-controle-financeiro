package br.com.api.controle.financeiro.controller;

import br.com.api.controle.financeiro.dto.DespesaDto;
import br.com.api.controle.financeiro.exception.DespesaDuplicadaException;
import br.com.api.controle.financeiro.service.DespesaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    @Autowired
    private DespesaService despesaService;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DespesaDto despesaDto, UriComponentsBuilder builder) throws DespesaDuplicadaException {
        var despesa = despesaService.cadastrarDespesa(despesaDto);
        var uri = builder.path("/despesas/{id}").buildAndExpand(despesa.getId()).toUri();

        return ResponseEntity.created(uri).body(despesa);
    }

    @GetMapping
    public Page<DespesaDto> listar(@PageableDefault(size = 10, sort = "id") Pageable pageable,
                                   @RequestParam(required = false) String descricao){

        if(descricao == null){
            return despesaService.obterDespesas(pageable);
        }

        return despesaService.obterReceitasPorDescricao(descricao, pageable);

    }

    @GetMapping("/{id}")
    public ResponseEntity obterDespesaPorId(@PathVariable(name = "id") Long id){
        var despesa = despesaService.obterDespesaPorId(id);

        return ResponseEntity.ok(despesa);
    }

    @GetMapping("/{ano}/{mes}")
    public ResponseEntity listarDespesasPorAnoMes(@PathVariable(name = "ano") int ano,
                                                  @PathVariable(name = "mes") int mes,
                                                  @PageableDefault(size = 10, sort = "id") Pageable pageable){
        var listaDespesas = despesaService.listaReceitasPorAnoMes(ano, mes, pageable);

        return ResponseEntity.ok(listaDespesas);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DespesaDto despesaDto){
        var despesaAtualizada = despesaService.atualizarDespesa(despesaDto);

        return ResponseEntity.ok(despesaAtualizada);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletarDespesaPorId(@PathVariable(name = "id") Long id){
        despesaService.excluirDespesa(id);

        return ResponseEntity.noContent().build();
    }
}
