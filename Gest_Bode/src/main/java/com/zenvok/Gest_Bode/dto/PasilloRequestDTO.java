package com.zenvok.Gest_Bode.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasilloRequestDTO {

    @NotBlank(message = "El nombre del pasillo no puede estar vacío")
    @Size(max = 50, message = "El nombre del pasillo no puede superar los 50 caracteres")
    private String nombrePasillo;
}
