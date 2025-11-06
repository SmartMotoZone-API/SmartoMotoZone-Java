package com.smartmotozone.api.smartmotozone_api.repository;

import com.smartmotozone.api.smartmotozone_api.model.Moto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MotoRepository extends JpaRepository<Moto, Long> {

    List<Moto> findByZonaCodigo(String codigoZona);

    List<Moto> findByModeloContainingIgnoreCase(String modelo);

    Page<Moto> findByModeloContainingIgnoreCase(String modelo, Pageable pageable);
}
