package com.zenvok.configuracion.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ConfiguracionDTO {

    private Long id;

    @NotBlank(message = "La clave de configuración es obligatoria")
    private String clave;

    @NotBlank(message = "El valor de configuración es obligatorio")
    private String valor;
}
    
