package com.example.recetario_app.services.role;

import com.example.recetario_app.domain.entities.auth.RoleEntity;
import com.example.recetario_app.domain.repositories.auth.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {


    private final RoleRepository roleRepository;
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Override
    public List<RoleEntity> getAllRoles() {
        logger.info("Buscando todos los roles - metodo getAllRoles");
        List<RoleEntity> roles = roleRepository.findAll();
        logger.info("Roles obtenidos: {}", roles.size());
        return roles;
    }

    @Override
    public Optional<RoleEntity> getRoleById(Long id) {
        logger.info("Buscando Role por ID {} - metodo getRoleById", id);
        return roleRepository.findById(id);
    }

    
}
