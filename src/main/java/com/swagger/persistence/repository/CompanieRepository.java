package com.swagger.persistence.repository;

import com.swagger.persistence.entity.CompanieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanieRepository extends JpaRepository<CompanieEntity, Long> {

}
