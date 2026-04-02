package com.github.eduardorisch.ms_pedidos.repositories;

import com.github.eduardorisch.ms_pedidos.entities.ItemDoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDoPedidoRepository extends JpaRepository<ItemDoPedido, Long> {
}
