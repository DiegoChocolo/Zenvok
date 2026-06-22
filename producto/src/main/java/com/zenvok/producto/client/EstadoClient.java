package com.zenvok.producto.client;

import com.zenvok.producto.dto.EstadoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "estado-client", url = "${estado.service.url}")
public interface EstadoClient {

    @GetMapping("/{id}")
    EstadoResponseDTO buscarPorId(@PathVariable("id") Long id);
}
