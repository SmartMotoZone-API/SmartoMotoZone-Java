package com.smartmotozone.api.smartmotozone_api.service;

import com.smartmotozone.api.smartmotozone_api.dto.FuncionarioDTO;
import com.smartmotozone.api.smartmotozone_api.exception.ResourceNotFoundException;
import com.smartmotozone.api.smartmotozone_api.model.Funcionario;
import com.smartmotozone.api.smartmotozone_api.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    @CacheEvict(value = {"funcionarios", "funcionarioPorId"}, allEntries = true)
    public Funcionario salvar(FuncionarioDTO dto) {
    Funcionario funcionario = new Funcionario();
    funcionario.setNome(dto.nome());
    funcionario.setCargo(dto.cargo());
        return funcionarioRepository.save(funcionario);
}

    @Cacheable(value = "funcionarios", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<Funcionario> listarTodos(Pageable pageable) {
        return funcionarioRepository.findAll(pageable);
}


    @Cacheable(value = "funcionarioPorId", key = "#id")
    public Funcionario buscarPorId(Long id) {
    return funcionarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado"));
}
    
    
    @CacheEvict(value = {"funcionarios", "funcionarioPorId"}, allEntries = true)
    public Funcionario atualizar(Long id, FuncionarioDTO dto) {
        Funcionario funcionario = buscarPorId(id);
        funcionario.setNome(dto.nome());
        funcionario.setCargo(dto.cargo());
        return funcionarioRepository.save(funcionario);
    }
    
    
    @CacheEvict(value = {"funcionarios", "funcionarioPorId"}, allEntries = true)
    public void deletar(Long id) {
        Funcionario funcionario = buscarPorId(id);
        funcionarioRepository.delete(funcionario);
    }
}
