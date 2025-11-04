package com.gestorlds.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAgendaItemRequest {

    @NotNull(message = "El número de orden es requerido")
    private Integer numeroOrden;

    @NotBlank(message = "El tipo es requerido")
    private String tipo;

    @NotBlank(message = "El título es requerido")
    private String titulo;

    private String descripcion;
    private UUID asignadoAId;
    private String emailAsignado;
    private Integer tiempoAsignado;
    private String notas;
}