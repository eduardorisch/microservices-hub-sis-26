package com.github.eduardorisch.ms.pagamentos.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("ms_pedidos")
public interface PedidoClient {
    @RequestMapping(method = RequestMethod.PUT,
    value = ("/pedidos/{pedido_id/pagamento/confirmado}"))
    void confirmarPagamento(@PathVariable Long id);
}
