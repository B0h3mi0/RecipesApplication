package com.example.RecetarioApp.service;

import com.example.RecetarioApp.domain.UsersEntity;
import com.example.RecetarioApp.exception.DatabaseTransactionException;
import com.example.RecetarioApp.exception.ResourceNotFoundException;
import com.example.RecetarioApp.repository.UsersRepository;
import com.example.RecetarioApp.request.UsersUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService{


    private UsersRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Override
    public List<UsersEntity> getAllUsers(){
        logger.info("Buscando todos los users - metodo getAllUserss");
        return userRepository.findAll();
    }

    @Override
    public Optional<UsersEntity> getUsersById(Long id) {
        logger.info("Buscando Users por ID {} - metodo getUsersById", id);
        return userRepository.findById(id);
    }

    @Override
    public UsersEntity createUsers(UsersEntity userToSave) {
        logger.info("Creando Users con request: {} - Metodo saveUsers", userToSave);
        try{
            UsersEntity savedUsers = userRepository.save(userToSave);
            logger.info("Users creado satisfactoriamente. Users ID: {} - metodo createUsers",savedUsers.getId());
            return savedUsers;
        } catch (Exception e) {
            logger.error("Error creando Users - metodo createUsers");
            throw new DatabaseTransactionException("Error creando Users", e);
        }
    }

    @Override
    public UsersEntity updateUsers(Long id, UsersUpdateRequest updateRequest) {
        logger.info("Actualizando user con ID: {} y request: {} - metodo updateUsers", id, updateRequest);

        // Buscar el user en la base de datos
        UsersEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Users no encontrado con ID: " + id));

        // Actualizar solo los campos que vengan en el request
        if (updateRequest.getUsername() != null) {
            logger.info("Actualizando nomUsers to: {} - metodo updateUsers", updateRequest.getUsername());
            user.setUsername(updateRequest.getUsername());
        }
        if (updateRequest.getPassword() != null) {
            logger.info("Actualizando apUsers : {} - metodo updateUsers", updateRequest.getPassword());
            user.setPassword(updateRequest.getPassword());
        }
        
        // Guardar los cambios
        logger.info("Guardando user - metodo updateUsers");
        UsersEntity updatedUsers = userRepository.save(user);
        logger.info("Users updated successfully. Users ID: {}", updatedUsers.getId());

        return updatedUsers;
    }

    @Override
    public void deleteUsersById(Long id) {
        logger.info("Eliminando Users por ID : {} - metodo deleteUsersById", id);
        Optional<UsersEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            logger.info("Users id {} no encontrado - metodo deleteUsersById", id);
            throw new ResourceNotFoundException("Users NO ENCONTRADO");
        }
        logger.info("Eliminando Users - metodo deleteUsersById" );
        logger.info("Id Users {} - metodo deleteUsersById" , id);
        userRepository.deleteById(id);
        logger.info("Users eliminado satisfactoriamente - metodo deleteUsersById");

    }
}
