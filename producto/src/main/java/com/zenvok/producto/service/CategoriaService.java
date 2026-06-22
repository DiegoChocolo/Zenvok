package com.zenvok.producto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zenvok.producto.dto.CategoriaRequestDTO;
import com.zenvok.producto.dto.CategoriaResponseDTO;
import com.zenvok.producto.model.Categoria;
import com.zenvok.producto.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listar() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDTO buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Categoria no encontrada con id: " + id));
        return convertirAResponse(categoria);
    }

    @Transactional
    public CategoriaResponseDTO guardar(CategoriaRequestDTO request) {
        Categoria categoria = new Categoria();
        categoria.setNombre(request.getNombre());
        categoria.setDescripcion(request.getDescripcion());

        Categoria guardada = categoriaRepository.save(categoria);
        return convertirAResponse(guardada);
    }

    @Transactional
    public CategoriaResponseDTO actualizar(Long id, CategoriaRequestDTO request) {
        Categoria categoria = categoriaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Categoria no encontrada con id: " + id));

        categoria.setNombre(request.getNombre());
        categoria.setDescripcion(request.getDescripcion());

        Categoria actualizada = categoriaRepository.save(categoria);
        return convertirAResponse(actualizada);
    }

    @Transactional
    public void eliminar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Categoria no encintrada con id: " + id));

        categoriaRepository.delete(categoria);
    }

    private CategoriaResponseDTO convertirAResponse(Categoria categoria) {
        CategoriaResponseDTO dto = new CategoriaResponseDTO();

        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());

        return dto;
    }
}
