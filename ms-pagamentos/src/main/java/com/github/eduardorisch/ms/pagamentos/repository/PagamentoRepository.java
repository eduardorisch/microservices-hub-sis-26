package com.github.eduardorisch.ms.pagamentos.repository;

import com.github.eduardorisch.ms.pagamentos.entities.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
