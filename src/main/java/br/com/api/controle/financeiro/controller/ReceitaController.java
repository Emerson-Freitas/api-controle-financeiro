package br.com.api.controle.financeiro.controller;

import br.com.api.controle.financeiro.dto.ReceitaDto;
import br.com.api.controle.financeiro.exception.ReceitaDuplicadaException;
import br.com.api.controle.financeiro.service.ReceitaService;
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
@RequestMapping("/receitas")
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid ReceitaDto receitaDto, UriComponentsBuilder builder) throws ReceitaDuplicadaException {
        var receita = receitaService.cadastrar(receitaDto);
        var uri = builder.path("/receitas/{id}").buildAndExpand(receita.getId()).toUri();

        return ResponseEntity.created(uri).body(receita);
    }

    @GetMapping("/{id}")
    public ResponseEntity buscarPorId(@PathVariable(name = "id") Long id){
       var receita = receitaService.obterReceitaPorId(id);

       return ResponseEntity.ok(receita);
    }


    @GetMapping
    public Page<ReceitaDto> listar(@PageableDefault(size = 10, sort = "id") Pageable pageable,
                                   @RequestParam(required = false) String descricao){

        if(descricao == null){
            return receitaService.obterReceitas(pageable);
        }

        return receitaService.obterReceitasPorDescricao(descricao, pageable);
    }

    @GetMapping("/{ano}/{mes}")
    public ResponseEntity listarReceitasPorAnoMes(@PathVariable(name = "ano") int ano,
                                                  @PathVariable(name = "mes") int mes,
                                                  @PageableDefault(size = 10, sort = "id") Pageable pageable){
        var listaReceitas = receitaService.listaReceitasPorAnoMes(ano, mes, pageable);

        return ResponseEntity.ok(listaReceitas);
    }


    @PutMapping
    @Transactional
    public ResponseEntity atualizarReceita(@RequestBody @Valid ReceitaDto receitaDto){
        var receitaAtualizada = receitaService.atualizarReceita(receitaDto);
        return ResponseEntity.ok(receitaAtualizada);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletarPorId(@PathVariable(name = "id") Long id){
        receitaService.excluirReceita(id);
        return ResponseEntity.noContent().build();
    }
}
