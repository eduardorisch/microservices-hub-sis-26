package com.github.eduardorisch.ms_pedidos.services;

import com.github.eduardorisch.ms_pedidos.dtos.ItemDoPedidoDTO;
import com.github.eduardorisch.ms_pedidos.dtos.PedidoDTO;
import com.github.eduardorisch.ms_pedidos.entities.ItemDoPedido;
import com.github.eduardorisch.ms_pedidos.entities.Pedido;
import com.github.eduardorisch.ms_pedidos.entities.Status;
import com.github.eduardorisch.ms_pedidos.exceptions.ResourceNotFoundException;
import com.github.eduardorisch.ms_pedidos.repositories.ItemDoPedidoRepository;
import com.github.eduardorisch.ms_pedidos.repositories.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pRepository;

    @Autowired
    private ItemDoPedidoRepository iRepository;

    @Transactional(readOnly = true)
    public List<PedidoDTO> findAllPedidos(){
        return pRepository.findAll().stream().map(PedidoDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public PedidoDTO findPedidoById(Long id){
        Pedido pedido = pRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado. ID: " + id)
        );
        return new PedidoDTO(pedido);
    }

    @Transactional
    public PedidoDTO savePedido(PedidoDTO dto){
        Pedido pedido = new Pedido();
        pedido.setData(dto.getData());
        pedido.setStatus(dto.getStatus());
        pedido.setData(LocalDate.now());
        pedido.setStatus(Status.CRIADO);
        mapDtoToPedido(dto, pedido);
        pedido.calcularValorTotalDoPedido();
        pedido = pRepository.save(pedido);
        return new PedidoDTO(pedido);
    }

    @Transactional
    public PedidoDTO updatePedido(Long id, PedidoDTO dto){
        try {
            Pedido pedido = pRepository.getReferenceById(id);
            pedido.getItens().clear();
            pedido.setData(LocalDate.now());
            pedido.setStatus(Status.CRIADO);
            mapDtoToPedido(dto, pedido);
            pedido.calcularValorTotalDoPedido();
            pedido = pRepository.save(pedido);
            return new PedidoDTO(pedido);
        } catch (EntityNotFoundException e ){
            throw new ResourceNotFoundException("Recurso não encontrado. id " + id);
        }
    }

    @Transactional
    public void deleteByID(Long id){
        if (!pRepository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado. ID " + id);
        }

        pRepository.deleteById(id);
    }

    private void mapDtoToPedido(PedidoDTO dto, Pedido pedido){
        pedido.setCpf(dto.getCpf());
        pedido.setNome(dto.getNome());

        for (ItemDoPedidoDTO itemDTO : dto.getItens()){
            ItemDoPedido itemPedido = new ItemDoPedido();
            itemPedido.setQuantidade(itemDTO.getQuantidade());
            itemPedido.setDescricao(itemDTO.getDescricao());
            itemPedido.setPrecoUnitario(itemDTO.getPrecoUnitario());
            itemPedido.setPedido(pedido);
            pedido.getItens().add(itemPedido);
        }
    }
}
