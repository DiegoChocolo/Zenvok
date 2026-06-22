package com.zenvok.Gest_Bode.service;

import com.zenvok.Gest_Bode.client.ProductoClient;
import com.zenvok.Gest_Bode.dto.ProdPasEstRequestDTO;
import com.zenvok.Gest_Bode.dto.ProdPasEstResponseDTO;
import com.zenvok.Gest_Bode.dto.ProductoResponseDTO;
import com.zenvok.Gest_Bode.model.Pas_Est;
import com.zenvok.Gest_Bode.model.ProdPasEst;
import com.zenvok.Gest_Bode.repository.PasEstRepository;
import com.zenvok.Gest_Bode.repository.ProdPasEstRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdPasEstService {

    @Autowired
    private ProductoClient productoClient;

    private final ProdPasEstRepository prodPasEstRepository;
    private final PasEstRepository pasEstRepository;

    public ProdPasEstResponseDTO guardar(ProdPasEstRequestDTO dto) {
        // Validamos que exista la combinación física de pasillo-estante
        Pas_Est pasEst = pasEstRepository.findById(dto.getPasEstId())
                .orElseThrow(() -> new RuntimeException("No existe la combinación Pasillo-Estante con ID: " + dto.getPasEstId()));

        validarProductoExiste(dto.getProductoId());
        
        ProdPasEst prodPasEst = new ProdPasEst();
        prodPasEst.setProductoId(dto.getProductoId());
        prodPasEst.setPasEst(pasEst);

        ProdPasEst guardado = prodPasEstRepository.save(prodPasEst);
        return mapearAResponse(guardado);
    }

    public List<ProdPasEstResponseDTO> obtenerTodos() {
        return prodPasEstRepository.findAll()
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public Optional<ProdPasEstResponseDTO> buscarPorProductoId(Long productoId) {
        return prodPasEstRepository.buscarUbicacionDeProducto(productoId)
                .map(this::mapearAResponse);
    }

    private void validarProductoExiste(Long productoId) {
    try {
        ProductoResponseDTO producto = productoClient.buscarPorId(productoId);

        if (producto == null || producto.getId() == null) {
            throw new RuntimeException("Producto no encontrado con ID: " + productoId);
        }

    } catch (Exception e) {
        throw new RuntimeException("Producto no encontrado con ID: " + productoId);
    }
}

    private ProdPasEstResponseDTO mapearAResponse(ProdPasEst entity) {
        ProdPasEstResponseDTO res = new ProdPasEstResponseDTO();
        res.setIdProdEst(entity.getIdProdEst());
        res.setProductoId(entity.getProductoId());
        res.setPasEstId(entity.getPasEst().getIdPasEst());
        
        // Desglosamos los strings desde los objetos relacionados para enriquecer el JSON de salida
        res.setNombrePasillo(entity.getPasEst().getPasillo().getNombrePasillo());
        res.setNombreEstante(entity.getPasEst().getEstante().getNombreEstante());
        return res;
    }
}