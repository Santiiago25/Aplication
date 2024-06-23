package com.swagger.persistence.repository;

import com.swagger.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByUsername(String username); //querys methods de JPA

//    @Query("SELECT u FROM UserEntity u WHERE u.username = ?") // esta es otra forma de hacer lo de arriba
//    Optional<UserEntity> findUser(String username);

}
