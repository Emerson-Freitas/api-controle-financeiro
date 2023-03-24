package br.com.api.controle.financeiro.controller;

import br.com.api.controle.financeiro.dto.ResumoDto;
import br.com.api.controle.financeiro.service.ResumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resumo")
public class ResumoController {

    @Autowired
    private ResumoService resumoService;

    @GetMapping("/{ano}/{mes}")
    public ResponseEntity listarResumoAnoMes(@PathVariable(name = "ano") int ano, @PathVariable(name = "mes") int mes){
        ResumoDto resumoDto = resumoService.resumoMes(ano, mes);

        return ResponseEntity.ok(resumoDto);
    }
}
