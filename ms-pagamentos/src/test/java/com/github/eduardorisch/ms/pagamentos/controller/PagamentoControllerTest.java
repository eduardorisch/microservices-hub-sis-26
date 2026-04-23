package com.github.eduardorisch.ms.pagamentos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.eduardorisch.ms.pagamentos.dto.PagamentoDTO;
import com.github.eduardorisch.ms.pagamentos.entities.Pagamento;
import com.github.eduardorisch.ms.pagamentos.exceptions.ResourceNotFoundException;
import com.github.eduardorisch.ms.pagamentos.service.PagamentoService;
import com.github.eduardorisch.ms.pagamentos.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.math.BigDecimal;
import java.util.List;

@WebMvcTest(PagamentoController.class)
public class PagamentoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PagamentoService service;
    private Pagamento pagamento;
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp(){
        existingId = 1L;
        nonExistingId = Long.MAX_VALUE;
        pagamento = Factory.createPagamento();
    }

    @Test
    void findAllPagamentoShouldReturnListPagamentoDto() throws Exception{
        PagamentoDTO inputDto = new PagamentoDTO(pagamento);
        List<PagamentoDTO> list = List.of(inputDto);
        Mockito.when(service.findAllPagamentos()).thenReturn(list);

        ResultActions result = mockMvc.perform(get("/pagamentos").accept(MediaType.APPLICATION_JSON));

        result.andDo(print());
        result.andExpect(status().isOk());
        result.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        result.andExpect(jsonPath("$").isArray());
        result.andExpect(jsonPath("$[0].id").value(pagamento.getId()));
        result.andExpect(jsonPath("$[0].val").value(pagamento.getVal().doubleValue()));

        Mockito.verify(service).findAllPagamentos();
        Mockito.verifyNoMoreInteractions(service);
    }

    @Test
    void findPagamentoByIdShouldReturnPagamentoDTOWhenIdExists() throws Exception{
        PagamentoDTO responseDto = new PagamentoDTO(pagamento);
        Mockito.when(service.findPagamentoById(existingId)).thenReturn(responseDto);

        mockMvc.perform(get("/pagamentos/{id}", existingId).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(existingId))
                .andExpect(jsonPath("$.val").value(pagamento.getVal().doubleValue()))
                .andExpect(jsonPath("$.status").value(pagamento.getStatus().name()))
                .andExpect(jsonPath("$.pedidoId").value(pagamento.getPedidoId()));

        Mockito.verify(service).findPagamentoById(existingId);
        Mockito.verifyNoMoreInteractions(service);
    }

    @Test
    void findPagamentoByIdShouldReturn404WhenIdDoesNotExist() throws Exception{
        Mockito.when(service.findPagamentoById(nonExistingId))
                .thenThrow(new ResourceNotFoundException("Recurso n encontrado. id " + nonExistingId));

        mockMvc.perform(get("/pagamentos/{id}", nonExistingId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

        Mockito.verify(service).findPagamentoById(nonExistingId);
        Mockito.verifyNoMoreInteractions(service);
    }

    @Test
    void createPagamentoShouldReturn201WhenValid() throws Exception{
        PagamentoDTO requestDto = new PagamentoDTO(Factory.createPagamentoSemId());

        String jsonRequestBody = objectMapper.writeValueAsString(requestDto);
        PagamentoDTO responseDto = new PagamentoDTO(pagamento);

        Mockito.when(service.savePagamento(any(PagamentoDTO.class))).thenReturn(responseDto);

        mockMvc.perform(post("/pagamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(pagamento.getId()))
                .andExpect(jsonPath("$.status").value(pagamento.getStatus().name()))
                .andExpect(jsonPath("$.val").value(pagamento.getVal().doubleValue()))
                .andExpect(jsonPath("$.pedidoId").value(pagamento.getPedidoId()));

        Mockito.verify(service).savePagamento(any(PagamentoDTO.class));
        Mockito.verifyNoMoreInteractions(service);
    }

    @Test
    void createPagamentoShouldReturn422WhenInvalid() throws Exception{
        Pagamento pagamentoInvalido = Factory.createPagamentoSemId();
        pagamentoInvalido.setVal(BigDecimal.valueOf(0));
        pagamentoInvalido.setNome(null);
        PagamentoDTO requestDTO = new PagamentoDTO(pagamentoInvalido);
        String jsonRequestBody = objectMapper.writeValueAsString(requestDTO);
        PagamentoDTO responseDTO = new PagamentoDTO(pagamentoInvalido);

        Mockito.when(service.savePagamento(any(PagamentoDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/pagamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

        Mockito.verifyNoInteractions(service);
    }

    @Test
    void updatePagamentoShouldReturn200WhenValid() throws Exception{
        PagamentoDTO requestDTO = new PagamentoDTO(Factory.createPagamento());
        String jsonRequestBody = objectMapper.writeValueAsString(requestDTO);
        PagamentoDTO responseDTO = new PagamentoDTO(pagamento);
        Mockito.when(service.updatePagamento(any(PagamentoDTO.class), eq(existingId)))
                .thenReturn(responseDTO);

        mockMvc.perform(put("/pagamentos/{id}", existingId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(existingId))
                .andExpect(jsonPath("$.status").value(pagamento.getStatus().name()))
                .andExpect(jsonPath("$.pedidoId").value(pagamento.getPedidoId()));

        Mockito.verify(service).updatePagamento(any(PagamentoDTO.class), eq(existingId));
        Mockito.verifyNoInteractions(service);
    }

    @Test
    void updatePagamentoShouldReturn422WhenInvalid() throws Exception{
        Pagamento pagamentoInvalido = Factory.createPagamento();
        pagamentoInvalido.setVal(BigDecimal.ZERO);
        pagamentoInvalido.setNome(null);
        PagamentoDTO requestDTO = new PagamentoDTO(pagamentoInvalido);
        String jsonRequestBody = objectMapper.writeValueAsString(requestDTO);

        mockMvc.perform(put("/pagamentos/{id}", existingId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

        Mockito.verifyNoInteractions(service);
    }

    @Test
    void updatePagamentoShouldReturn404WhenIdDoesNotExists() throws Exception{
        PagamentoDTO requestDTO = new PagamentoDTO(Factory.createPagamentoSemId());
        String jsonRequestBody = objectMapper.writeValueAsString(requestDTO);

        Mockito.when(service.updatePagamento(any(PagamentoDTO.class), eq(nonExistingId)))
                .thenThrow(new ResourceNotFoundException("Recurso não encontrado. ID: " + nonExistingId));

        mockMvc.perform(put("/pagamentos/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestBody))
                .andExpect(status().isNotFound())
                .andDo(print());

        Mockito.verify(service).updatePagamento(any(PagamentoDTO.class), eq(nonExistingId));
        Mockito.verifyNoInteractions(service);
    }

    @Test
    void deletePagamentoShouldReturn204WhenIdExists() throws Exception{
        Mockito.doNothing().when(service).deletePagamentoById(existingId);

        mockMvc.perform(delete("/pagamentos/{id}", existingId))
                .andExpect(status().isNoContent());

        Mockito.verify(service).deletePagamentoById(existingId);
        Mockito.verifyNoMoreInteractions(service);
    }

    @Test
    void deletePagamentoShouldReturn404WhenIdDoesNotExist() throws Exception{
        Mockito.doThrow(new ResourceNotFoundException("Recurso não encontrado. ID " + nonExistingId))
                .when(service).deletePagamentoById(nonExistingId);

        mockMvc.perform(delete("/pagamentos/{id}", nonExistingId))
                .andExpect(status().isNotFound());

        Mockito.verify(service).deletePagamentoById(nonExistingId);
        Mockito.verifyNoMoreInteractions(service);
    }
}
