package com.smartmotozone.api.smartmotozone_api.repository;

import com.smartmotozone.api.smartmotozone_api.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    Page<Movimentacao> findByMotoId(Long motoId, Pageable pageable);
}
