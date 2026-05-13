package com.github.eduardorisch.ms.pagamentos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_pagamento")
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private BigDecimal val;
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 16)
    private String numCartao;

    @Column(nullable = false, length = 5)
    private String validade;
    @Column(nullable = false, length = 3)
    private String codSeg;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(nullable = false)
    private Long pedidoId;
}
