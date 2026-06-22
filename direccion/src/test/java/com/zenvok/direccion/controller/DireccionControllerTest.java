package com.zenvok.direccion.controller;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
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
import com.zenvok.direccion.dto.DireccionRequestDTO;
import com.zenvok.direccion.dto.DireccionResponseDTO;
import com.zenvok.direccion.service.DireccionService;

@WebMvcTest(DireccionController.class)
@DisplayName("Test de Capa Web de DireccionController")
class DireccionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private DireccionService direccionService;

    @Test
    @DisplayName("GET /api/direcciones retorna estado 200 y la lista de direcciones")
    void listar_DebeRetornarStatus200YLista() throws Exception {
        DireccionResponseDTO responseDto = new DireccionResponseDTO(
            3L, "Arturo Prat", 100, "Block A, Depto 201", 1L, 2L, 45L
        );

        when(direccionService.listarDirecciones()).thenReturn(List.of(responseDto));

        mockMvc.perform(get("/api/direcciones")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(3L))
                .andExpect(jsonPath("$[0].calle").value("Arturo Prat"))
                .andExpect(jsonPath("$[0].numeracion").value(100))
                .andExpect(jsonPath("$[0].block").value("Block A, Depto 201"))
                .andExpect(jsonPath("$[0].estadoId").value(1L))
                .andExpect(jsonPath("$[0].comunaId").value(2L))
                .andExpect(jsonPath("$[0].usuarioId").value(45L));
    }

    @Test
    @DisplayName("POST /api/direcciones crea una dirección y retorna 201 Created")
    void crear_DebeRetornar201_CuandoElDtoEsValido() throws Exception {
        DireccionRequestDTO requestDto = new DireccionRequestDTO(
            "Arturo Prat", 100, "Block A, Depto 201", 1L, 2L, 45L
        );

        DireccionResponseDTO responseDto = new DireccionResponseDTO(
            3L, "Arturo Prat", 100, "Block A, Depto 201", 1L, 2L, 45L
        );

        when(direccionService.crearDireccion(any(DireccionRequestDTO.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/direcciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.calle").value("Arturo Prat"))
                .andExpect(jsonPath("$.numeracion").value(100))
                .andExpect(jsonPath("$.estadoId").value(1L))
                .andExpect(jsonPath("$.usuarioId").value(45L));
    }
}