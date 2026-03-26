package com.github.eduardorisch.ms_pedidos.controller;

import com.github.eduardorisch.ms_pedidos.dtos.PedidoDTO;
import com.github.eduardorisch.ms_pedidos.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService service;

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> getAllPedidos(){
        List<PedidoDTO> list = service.findAllPedidos();
        return ResponseEntity.ok(list);
    }
}
