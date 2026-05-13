package com.github.eduardorisch.ms.pagamentos.service;

import com.github.eduardorisch.ms.pagamentos.dto.PagamentoDTO;
import com.github.eduardorisch.ms.pagamentos.entities.Pagamento;
import com.github.eduardorisch.ms.pagamentos.entities.Status;
import com.github.eduardorisch.ms.pagamentos.exceptions.ResourceNotFoundException;
import com.github.eduardorisch.ms.pagamentos.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repository;

    @Transactional(readOnly = true)
    public List<PagamentoDTO> findAllPagamentos() {
        List<Pagamento> list = repository.findAll();
        return list.stream().map(PagamentoDTO :: new ).toList();
    }

    @Transactional(readOnly = true)
    public PagamentoDTO findPagamentoById(Long id) {
        Pagamento pagamento = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso n encontrado. ID " + id)
        );
        return new PagamentoDTO(pagamento);
    }

    @Transactional
    public PagamentoDTO savePagamento(PagamentoDTO dto) {
        Pagamento pagamento = new Pagamento();
        mapperDtoToPagamento(dto, pagamento);
        pagamento.setStatus(Status.CRIADO);
        pagamento = repository.save(pagamento);
        return new PagamentoDTO(pagamento);
    }

    @Transactional
    public PagamentoDTO updatePagamento (PagamentoDTO dto, Long id){
        try{
            Pagamento pagamento = repository.getReferenceById(id);
            mapperDtoToPagamento(dto, pagamento);
            pagamento.setStatus(dto.getStatus());
            pagamento = repository.save(pagamento);
            return new PagamentoDTO(pagamento);
        } catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Recurso n encontrado. ID " + id);
        }
    }

    @Transactional
    public void deletePagamentoById(Long id){
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Recurso n encontrado. id " + id);
        }
        repository.deleteById(id);
    }

    private void mapperDtoToPagamento(PagamentoDTO dto, Pagamento pag){
        pag.setCodSeg(dto.getCodSeg());
        pag.setNome(dto.getNome());
        pag.setVal(dto.getVal());

        pag.setNumCartao(dto.getNumCartao());

        pag.setPedidoId(dto.getPedidoId());
        pag.setValidade(dto.getValidade());
    }
}
