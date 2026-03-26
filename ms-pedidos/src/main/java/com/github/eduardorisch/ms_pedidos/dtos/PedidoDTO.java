package com.github.eduardorisch.ms_pedidos.dtos;

import com.github.eduardorisch.ms_pedidos.entities.ItemDoPedido;
import com.github.eduardorisch.ms_pedidos.entities.Pedido;
import com.github.eduardorisch.ms_pedidos.entities.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PedidoDTO {
    private Long id;
    @NotBlank(message = "Nome requerido")
    @Size(min = 3, max = 100, message = "o nome deve ter entre 3 a 100 caracteres")
    private String nome;
    @NotBlank(message = "CPF requerido")
    @Size(min = 11, max = 11, message = "O cpf deve ter 11 caracteres")
    private String cpf;
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private Status status;
    private BigDecimal valorTotal;
    private List<@Valid ItemDoPedidoDTO> itens = new ArrayList<>();

    public PedidoDTO (Pedido pedido){
        id = pedido.getId();
        nome = pedido.getNome();
        cpf = pedido.getCpf();
        data = pedido.getData();
        status = pedido.getStatus();
        valorTotal = pedido.getValorTotal();

        for (ItemDoPedido item : pedido.getItens()){
            ItemDoPedidoDTO itemDto = new ItemDoPedidoDTO(item);
            itens.add(itemDto);
        }
    }
}
