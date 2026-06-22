package com.zenvok.envios.client;

import com.zenvok.envios.dto.DireccionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "direccion-client", url = "${ms.direccion.url}")
public interface DireccionClient {

    @GetMapping("/api/direccion/{id}")
    DireccionResponse buscarPorId(@PathVariable("id") Long id);
}
