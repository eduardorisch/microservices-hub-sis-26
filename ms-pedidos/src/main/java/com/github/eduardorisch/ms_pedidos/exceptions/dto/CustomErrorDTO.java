package com.github.eduardorisch.ms_pedidos.exceptions.dto;

import com.github.eduardorisch.ms_pedidos.exceptions.PedidoPagoException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomErrorDTO {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String path;

    //public CustomErrorDTO(Instant now, Integer value, String message, String requestURI) {
    //    timestamp = now;
    //    status = value;
    //    error = message;
    //    path = requestURI;
//
    //}
}
