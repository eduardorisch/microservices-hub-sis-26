package com.github.eduardorisch.ms.pagamentos.dto;

import com.github.eduardorisch.ms.pagamentos.entities.Pagamento;
import com.github.eduardorisch.ms.pagamentos.entities.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//public class PagamentoDTO {
//    private Long id;
//
//    @NotNull(message = "O campo é requerido")
//    @Positive(message = "O campo tem que ser positivo")
//    private BigDecimal val;
//
//    @NotBlank(message = "O campo é requerido")
//    @Size(min = 3, max = 50, message = "O campo deve ter entre 3 a 50 letras")
//    private String nome;
//
////    @NotBlank(message = "O campo é requerido")
////    @Size(min = 16, max = 16, message = "O campo deve ter 16 letras")
//    private String nCartao;
//
//    @NotBlank(message = "O campo é requerido")
//    @Size(min = 5, max = 5, message = "O campo deve ter 5 letras")
//    private String validade;
//
//    @NotBlank(message = "O campo é requerido")
//    @Size(min = 3, max = 3, message = "O campo deve ter 3 letras")
//    private String codSeg;
//
//    private Status status;
//
//    @NotNull(message = "O campo é requerido")
//    private Long pedidoId;
//
//    public PagamentoDTO(Pagamento pagamento) {
//        id = pagamento.getId();
//        val = pagamento.getVal();
//        nome = pagamento.getNome();
//
//      nCartao = pagamento.getNCartao();
//
//        validade = pagamento.getValidade();
//        codSeg = pagamento.getCodSeg();
//        status = pagamento.getStatus();
//        pedidoId = pagamento.getPedidoId();
//    }
//}

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PagamentoDTO {
    private Long id;

    @NotNull(message = "O campo é requirido")
    @Positive(message = "O valor tem que ser positivo")
    private BigDecimal val;

    @NotBlank(message = "O campo é requirido")
    @Size(min = 3, max = 50, message = "O campo deve ter entre 3 e 50 caractreres")
    private String nome;

    @NotBlank(message = "O campo é requirido")
    @Size(min = 16, max = 16, message = "O campo deve ter 16 caractreres")
    private String nCartao;

    @NotBlank(message = "O campo é requirido")
    @Size(min = 5, max = 5, message = "O campo deve ter 5 caractreres")
    private String validade;

    @NotBlank(message = "O campo é requirido")
    @Size(min = 3, max = 3, message = "O campo deve ter 3 caractreres")
    private String codSeg;

    private Status status;

    @NotNull(message = "O campo é requirido")
    private Long pedidoId;

    public PagamentoDTO(Pagamento pag) {
        id = pag.getId();
        val = pag.getVal();
        nome = pag.getNome();
        nCartao = pag.getNCartao();
        validade = pag.getValidade();
        codSeg = pag.getCodSeg();
        status = pag.getStatus();
        pedidoId = pag.getPedidoId();
    }
}
