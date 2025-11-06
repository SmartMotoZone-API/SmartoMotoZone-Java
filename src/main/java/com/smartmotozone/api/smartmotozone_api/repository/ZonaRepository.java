package com.smartmotozone.api.smartmotozone_api.repository;

import com.smartmotozone.api.smartmotozone_api.model.Zona;



import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ZonaRepository extends JpaRepository<Zona, Long> {
    Optional<Zona> findByCodigo(String codigo);
    
    List<Zona> findByDescricaoContainingIgnoreCase(String descricao);

    Optional<Zona> findByDescricao(String descricao);

}
