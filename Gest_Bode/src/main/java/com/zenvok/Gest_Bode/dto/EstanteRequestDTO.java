package com.zenvok.Gest_Bode.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstanteRequestDTO {

    @NotBlank(message = "El nombre del estante no puede estar vacío")
    @Size(max = 50, message = "El nombre del estante no puede superar los 50 caracteres")
    private String nombreEstante;
}
