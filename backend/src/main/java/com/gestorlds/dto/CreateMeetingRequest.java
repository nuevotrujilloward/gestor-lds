package com.gestorlds.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMeetingRequest {

    @NotNull(message = "El tipo de reunión es requerido")
    private UUID meetingTypeId;

    @NotNull(message = "La fecha es requerida")
    private LocalDateTime fechaProgramada;

    private String ubicacion;

    @NotNull(message = "El conductor es requerido")
    private UUID conductorId;

    private String notasGenerales;
}