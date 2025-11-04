package com.gestorlds.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendaItemDto {
    private UUID id;
    private UUID meetingId;
    private Integer numeroOrden;
    private String tipo;
    private String titulo;
    private String descripcion;
    private String asignadoNombre;
    private String emailAsignado;
    private Integer tiempoAsignado;
    private Boolean confirmado;
    private Boolean presentado;
}