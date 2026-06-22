package com.zenvok.producto.service;

import com.zenvok.producto.client.EstadoClient;
import com.zenvok.producto.dto.EstadoResponseDTO;
import com.zenvok.producto.dto.ProductoRequestDTO;
import com.zenvok.producto.dto.ProductoResponseDTO;
import com.zenvok.producto.exception.CategoriaNotFoundException;
import com.zenvok.producto.exception.ProductoNotFoundException;
import com.zenvok.producto.model.Categoria;
import com.zenvok.producto.model.Producto;
import com.zenvok.producto.repository.CategoriaRepository;
import com.zenvok.producto.repository.ProductoRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private EstadoClient estadoClient;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductoService.class);

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> listar() {
        logger.info("Listando productos");
        
        return productoRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductoResponseDTO buscarPorId(Long id) {
        logger.info("Buscando producto con ID: {}", id);
        
        Producto producto = productoRepository.findById(id)
        .orElseThrow(() -> new ProductoNotFoundException(id));

        return convertirAResponse(producto);
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> listarPorCategoria(Long categoriaId) {
        logger.info("Listando productos por categoria con id: {}", categoriaId);

        return productoRepository.findByCategoriaId(categoriaId)
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Transactional
    public ProductoResponseDTO guardar(ProductoRequestDTO request) {
        logger.info("Guardando nuevo producto: {}", request.getNombre());

        validarEstadoExiste(request.getEstadoId());

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
        .orElseThrow(() -> new CategoriaNotFoundException(request.getCategoriaId()));

        Producto producto = new Producto();

        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setEstadoId(request.getEstadoId());
        producto.setCategoria(categoria);

        Producto guardado = productoRepository.save(producto);

        return convertirAResponse(guardado);
    }
    @Transactional
    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO request) {
        logger.info("Actualizando producto con id: {}", id);

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));

        validarEstadoExiste(request.getEstadoId());

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new CategoriaNotFoundException(request.getCategoriaId()));

        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setEstadoId(request.getEstadoId());
        producto.setCategoria(categoria);

        Producto actualizado = productoRepository.save(producto);

        return convertirAResponse(actualizado);
    }

    @Transactional
public void eliminar(Long id) {
    logger.warn("Eliminando producto con id: {}", id);

    Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new ProductoNotFoundException(id));

    productoRepository.delete(producto);
}

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> buscarPorNombre(String nombre){
        logger.info("Bsucando productos por nombre: {}", nombre);

        return productoRepository.findByNombreContainingIgnoreCase(nombre)
        .stream()
        .map(this::convertirAResponse)
        .toList();
    }

    private void validarEstadoExiste(Long estadoId) {

        try {
            EstadoResponseDTO estado = estadoClient.buscarPorId(estadoId);

            if (estado == null || estado.getId() == null) {
                throw new RuntimeException("Estado no encontrado con ID: " + estadoId);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al comunicarse con el microservicio Estado: " + e.getMessage());
        }
    }

    private ProductoResponseDTO convertirAResponse(Producto producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();

        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setEstadoId(producto.getEstadoId());
        dto.setCategoriaId(producto.getCategoria().getId());
        dto.setCategoriaNombre(producto.getCategoria().getNombre());

        return dto;
    }
}