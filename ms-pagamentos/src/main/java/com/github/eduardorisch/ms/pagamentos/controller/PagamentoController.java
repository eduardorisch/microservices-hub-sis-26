package com.github.eduardorisch.ms.pagamentos.controller;

import com.github.eduardorisch.ms.pagamentos.dto.PagamentoDTO;
import com.github.eduardorisch.ms.pagamentos.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @GetMapping
    public ResponseEntity<List<PagamentoDTO>> getAll (){
        List<PagamentoDTO> list = service.findAllPagamentos();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> getByid(@PathVariable Long id){
        PagamentoDTO dto = service.findPagamentoById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> createPagamento(@RequestBody @Valid PagamentoDTO dto){
        dto = service.savePagamento(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(dto);
    }
}
