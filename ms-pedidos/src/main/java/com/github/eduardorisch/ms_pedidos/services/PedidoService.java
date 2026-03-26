package com.github.eduardorisch.ms_pedidos.services;

import com.github.eduardorisch.ms_pedidos.dtos.PedidoDTO;
import com.github.eduardorisch.ms_pedidos.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository repository;

    @Transactional(readOnly = true)
    public List<PedidoDTO> findAllPedidos(){
        return repository.findAll().stream().map(PedidoDTO::new).toList();
    }
}
