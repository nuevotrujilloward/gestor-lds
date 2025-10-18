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

public class UserDto {
    private UUID id;
    private String email;
    private String nombre;
    private String rolNombre;
    private Boolean activo;
}
