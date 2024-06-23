package com.swagger.persistence.repository;

import com.swagger.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    //query que nos devuelve los roles, devolvemos una lista de roles
    List<RoleEntity> findRoleEntitiesByRoleEnumIn(List<String> roleNames);
}
