package com.gestorlds.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingDto {
    private UUID id;
    private String meetingTypeName;
    private LocalDateTime fechaProgramada;
    private String ubicacion;
    private String conductorName;
    private String estado;
    private String notasGenerales;
}