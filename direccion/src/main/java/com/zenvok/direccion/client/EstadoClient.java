package com.zenvok.direccion.client;

import com.zenvok.direccion.dto.EstadoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "estado-client", url = "${ms.estado.url}")
public interface EstadoClient {

    @GetMapping("/api/estados/{id}")
    EstadoResponseDTO buscarPorId(@PathVariable("id") Long id);
}
