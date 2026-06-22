package com.zenvok.direccion.controller;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webclient.test.autoconfigure.WebClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zenvok.direccion.dto.ComunaRequestDTO;
import com.zenvok.direccion.dto.ComunaResponseDTO;
import com.zenvok.direccion.service.ComunaService;

@WebClientTest(ComunaController.class)
@DisplayName("Test de Capa Web de ComunaController")
class ComunaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean 
    private ComunaService comunaService;

    @Test
    @DisplayName("GET /api/comunas retorna un estado 200 y la lista de comunas")
    void listar_DebeRetornarStatus200YLista() throws Exception {
    
        ComunaResponseDTO responseDto = new ComunaResponseDTO();
        responseDto.setId(1L);
        responseDto.setNombreComuna("SANTIAGO CENTRO");
        
        when(comunaService.listarComunas()).thenReturn(List.of(responseDto));

       
        mockMvc.perform(get("/api/comunas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombreComuna").value("SANTIAGO CENTRO"));
    }

    @Test
    @DisplayName("POST /api/comunas crea una comuna y retorna 201 Created")
    void crear_DebeRetornar201_CuandoElDtoEsValido() throws Exception {
        
        ComunaRequestDTO requestDto = new ComunaRequestDTO();
        requestDto.setNombreComuna("SANTIAGO CENTRO");

        ComunaResponseDTO responseDto = new ComunaResponseDTO();
        responseDto.setId(1L);
        responseDto.setNombreComuna("SANTIAGO CENTRO");

        when(comunaService.crearComuna(any(ComunaRequestDTO.class))).thenReturn(responseDto);

     
        mockMvc.perform(post("/api/comunas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))) 
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombreComuna").value("SANTIAGO CENTRO"));
    }

    @Test
    @DisplayName("GET /api/comunas/{id} retorna 200 cuando la comuna existe")
    void obtenerPorId_DebeRetornar200_CuandoExiste() throws Exception {
        ComunaResponseDTO responseDto = new ComunaResponseDTO(1L, "SANTIAGO CENTRO");
        when(comunaService.obtenerPorId(1L)).thenReturn(responseDto);

        mockMvc.perform(get("/api/comunas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("PUT /api/comunas/{id} retorna 200 cuando se actualiza correctamente")
    void actualizar_DebeRetornar200_CuandoEsValido() throws Exception {
        ComunaRequestDTO requestDto = new ComunaRequestDTO("PROVIDENCIA");
        ComunaResponseDTO responseDto = new ComunaResponseDTO(1L, "PROVIDENCIA");

        when(comunaService.actualizarComuna(eq(1L), any(ComunaRequestDTO.class))).thenReturn(responseDto);

        mockMvc.perform(put("/api/comunas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreComuna").value("PROVIDENCIA"));
    }

    @Test
    @DisplayName("DELETE /api/comunas/{id} retorna 204 cuando se elimina correctamente")
    void eliminar_DebeRetornar204_CuandoExiste() throws Exception {
        mockMvc.perform(delete("/api/comunas/1"))
                .andExpect(status().isNoContent());
    }
}