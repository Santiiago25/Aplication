package com.swagger.service.interfaces;

import com.swagger.persistence.entity.CompanieEntity;
import com.swagger.persistence.entity.UserEntity;
import com.swagger.presentation.dto.CompanyDto;

import java.util.List;

public interface ICompanieService {

    //listar las empresas
    List<CompanyDto> listAll();

    List<UserEntity> listAllUsers();

    //recibe como dato cliente y lo guarda (POST, PUT)
    CompanieEntity save(CompanyDto company);

    //busca por id (GET)
    CompanieEntity findById(Long id);

    //eliminar el cliente (DELETE)
    void delete(CompanieEntity company);

    //existe el id
    boolean existsById(Long id);

}
