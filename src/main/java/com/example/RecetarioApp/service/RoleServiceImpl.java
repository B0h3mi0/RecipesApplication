package com.example.RecetarioApp.service;

import com.example.RecetarioApp.domain.RoleEntity;
import com.example.RecetarioApp.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl {


    private RoleRepository roleRepository;
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Override
    public List<RoleEntity> getAllRole(){
        logger.info("Buscando todos los rolas - metodo getAllRoles");
        return roleRepository.findAll();
    }

    @Override
    public Optional<RoleEntity> getRoleById(Long id) {
        logger.info("Buscando Role por ID {} - metodo getRoleById", id);
        return roleRepository.findById(id);
    }

    
}
