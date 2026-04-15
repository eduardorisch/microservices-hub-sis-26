package com.github.eduardorisch.ms.pagamentos.service;

import com.github.eduardorisch.ms.pagamentos.dto.PagamentoDTO;
import com.github.eduardorisch.ms.pagamentos.entities.Pagamento;
import com.github.eduardorisch.ms.pagamentos.exceptions.ResourceNotFoundException;
import com.github.eduardorisch.ms.pagamentos.repository.PagamentoRepository;
import com.github.eduardorisch.ms.pagamentos.tests.Factory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class PagmamentoServiceTest {

    @Mock
    public PagamentoRepository repository;

    @InjectMocks
    private PagamentoService service;

    private Long existingId;
    private Long nonExistingId;

    private Pagamento pagamento;

    @BeforeEach
    void setUp(){
        existingId = 1L;
        nonExistingId = Long.MAX_VALUE;
        pagamento = Factory.createPagamento();
    }

    @Test
    void deleteByIdShouldDeleteWhenIdExists(){
        Mockito.when(repository.existsById(existingId)).thenReturn(true);

        service.deletePagamentoById(existingId);

        Mockito.verify(repository).existsById(existingId);

        Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
    }

    @Test
    void deleteByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
        Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> {
                    service.deletePagamentoById(nonExistingId);
                });

        Mockito.verify(repository).existsById(nonExistingId);

        Mockito.verify(repository, Mockito.never()).deleteById(Mockito.anyLong());
    }

    @Test
    void findPagamentoByIdShouldReturnPagamentoDTOWhenIdExists(){
        Mockito.when(repository.findById(existingId)) . thenReturn(Optional.of(pagamento));

        PagamentoDTO result = service.findPagamentoById(existingId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(pagamento.getId(), result.getId());
        Assertions.assertEquals(pagamento.getVal(), result.getVal());

        Mockito.verify(repository).findById(existingId);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("dado parametros validos e id nulo quando chamar salvar pagamento então deve gerar " +
            "id e persistir um pagamento")
    void givenValidParamsAndIsNull_whenSave_thenShouldPersistPagamento(){
        Mockito.when(repository.save(any(Pagamento.class))).thenReturn(pagamento);
        pagamento.setId(null);

        PagamentoDTO inputDto = new PagamentoDTO(pagamento);
        PagamentoDTO result = service.savePagamento(inputDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(pagamento.getId(), result.getId());

        Mockito.verify(repository).save(any(Pagamento.class));
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    void updatePagamentoShouldReturnPagamentoDTOWhenIdExists(){
        Long id = pagamento.getId();

        Mockito.when(repository.getReferenceById(id)).thenReturn(pagamento);
        Mockito.when(repository.save(any(Pagamento.class))).thenReturn(pagamento);

        PagamentoDTO result = service.updatePagamento(new PagamentoDTO(pagamento), id);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(id, result.getId());
        Assertions.assertEquals(pagamento.getVal(), result.getVal());
        Mockito.verify(repository).getReferenceById(id);
        Mockito.verify(repository).save(Mockito.any(Pagamento.class));
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    void updatePagamentoShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
        Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        PagamentoDTO inputDto = new PagamentoDTO(pagamento);

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> service.updatePagamento(inputDto, nonExistingId));

        Mockito.verify(repository).getReferenceById(nonExistingId);
        Mockito.verify(repository, Mockito.never()).save(Mockito.any(Pagamento.class));
        Mockito.verifyNoMoreInteractions(repository);
    }
}
