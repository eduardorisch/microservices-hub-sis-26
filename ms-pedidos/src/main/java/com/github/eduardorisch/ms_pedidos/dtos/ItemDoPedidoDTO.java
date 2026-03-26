package com.github.eduardorisch.ms_pedidos.dtos;

import com.github.eduardorisch.ms_pedidos.entities.ItemDoPedido;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ItemDoPedidoDTO {
    private Long id;

    @NotNull(message = "Quantidade requerida")
    @Positive(message = "Quantidade deve ser positiva")
    private Integer quantidade;
    @NotBlank(message = "Descrição requerida")
    private String descricao;
    @NotNull(message = "Preço unitario requerido")
    @Positive(message = "Quantidade deve ser positiva")
    private BigDecimal precoUnitario;

    public ItemDoPedidoDTO(ItemDoPedido itemDoPedido){
        id = itemDoPedido.getId();
        quantidade = itemDoPedido.getQuantidade();
        descricao = itemDoPedido.getDescricao();
        precoUnitario = itemDoPedido.getPrecoUnitario();
    }
}
