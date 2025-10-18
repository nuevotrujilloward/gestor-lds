package com.gestorlds.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "El email es requerido")
    @Email(message = "El email debe ser valido")
    private String email;

    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    @NotBlank(message = "La contraseña es requerida")
    private String password;

    private UUID rolId;

}
