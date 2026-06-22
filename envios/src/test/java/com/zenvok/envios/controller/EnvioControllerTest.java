package com.zenvok.envios.controller;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zenvok.envios.dto.EnvioRequest;
import com.zenvok.envios.dto.EnvioResponse;
import com.zenvok.envios.service.EnvioService;

@WebMvcTest(EnvioController.class)
@DisplayName("Test de Capa Web de EnvioController")
class EnvioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private EnvioService envioService;

    @Test
    @DisplayName("GET /api/envios retorna estado 200 y la lista de envíos")
    void listar_DebeRetornarStatus200YLista() throws Exception {
        EnvioResponse response = new EnvioResponse(5L, "2026-06-21", "2026-06-22", "Comentario", 1L, 10L, 2L);

        when(envioService.listarEnvios()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/envios").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(5L))
            .andExpect(jsonPath("$[0].fechaEnvio").value("2026-06-21"))
            .andExpect(jsonPath("$[0].direccionId").value(1L))
            .andExpect(jsonPath("$[0].ventaId").value(10L))
            .andExpect(jsonPath("$[0].estadoId").value(2L));
    }

    @Test
    @DisplayName("POST /api/envios crea un envío y retorna 201 Created")
    void crear_DebeRetornar201_CuandoElDtoEsValido() throws Exception {
        EnvioRequest request = new EnvioRequest();
        request.setFechaEnvio("2026-06-21");
        request.setFechaEmbargue("2026-06-22");
        request.setComentarios("Comentario");
        request.setDireccionId(1L);
        request.setVentaId(10L);
        request.setEstadoId(2L);

        EnvioResponse response = new EnvioResponse(5L, "2026-06-21", "2026-06-22", "Comentario", 1L, 10L, 2L);

        when(envioService.crearEnvio(any(EnvioRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/envios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(5L))
            .andExpect(jsonPath("$.fechaEnvio").value("2026-06-21"))
            .andExpect(jsonPath("$.direccionId").value(1L));
    }

    @Test
    @DisplayName("GET /api/envios/{id} retorna 200 con el envío cuando existe")
    void obtenerPorId_DebeRetornar200_CuandoExiste() throws Exception {
        EnvioResponse response = new EnvioResponse(5L, "2026-06-21", "2026-06-22", "Comentario", 1L, 10L, 2L);

        when(envioService.obtenerPorId(5L)).thenReturn(response);

        mockMvc.perform(get("/api/envios/5").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(5L));
    }

    @Test
    @DisplayName("PUT /api/envios/{id} actualiza y retorna 200")
    void actualizar_DebeRetornar200_CuandoEsValido() throws Exception {
        EnvioRequest request = new EnvioRequest();
        request.setFechaEnvio("2026-06-25");
        request.setFechaEmbargue("2026-06-26");
        request.setDireccionId(1L);
        request.setVentaId(10L);
        request.setEstadoId(3L);

        EnvioResponse response = new EnvioResponse(5L, "2026-06-25", "2026-06-26", null, 1L, 10L, 3L);

        when(envioService.actualizarEnvio(eq(5L), any(EnvioRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/envios/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.estadoId").value(3L));
    }

    @Test
    @DisplayName("DELETE /api/envios/{id} retorna 204 No Content")
    void eliminar_DebeRetornar204() throws Exception {
        mockMvc.perform(delete("/api/envios/5"))
            .andExpect(status().isNoContent());
    }
}