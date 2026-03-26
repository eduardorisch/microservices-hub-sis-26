package com.github.eduardorisch.ms_pedidos.dtos;

import com.github.eduardorisch.ms_pedidos.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StautsDTO {
    private Status status;
}
