package com.swagger.service.implementation;


import com.swagger.persistence.entity.CompanieEntity;
import com.swagger.persistence.entity.UserEntity;
import com.swagger.persistence.repository.CompanieRepository;
import com.swagger.persistence.repository.UserRepository;
import com.swagger.presentation.dto.CompanyDto;
import com.swagger.service.interfaces.ICompanieService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanieServiceImpl implements ICompanieService {

    @Autowired
    private CompanieRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<CompanyDto> listAll() {
        return companyRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserEntity> listAllUsers() {
        return (List) userRepository.findAll();
    }

    @Transactional
    @Override
    public CompanieEntity save(CompanyDto companyDto) {
        CompanieEntity company = CompanieEntity.builder()
                .id(companyDto.getId())
                .nit(companyDto.getNit())
                .company(companyDto.getCompany())
                .manager(companyDto.getManager())
                .email(companyDto.getEmail())
                .phone(companyDto.getPhone())
                .address(companyDto.getAddress())
                .department(companyDto.getDepartment())
                .municipality(companyDto.getMunicipality())
                .build();
        return companyRepository.save(company);
    }

    @Transactional
    @Override
    public CompanieEntity findById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(CompanieEntity company) {
        companyRepository.delete(company);
    }

    @Override
    public boolean existsById(Long id) {
        return companyRepository.existsById(id);
    }

    private CompanyDto convertToDto(CompanieEntity company) {
        return CompanyDto.builder()
                .id(company.getId())
                .nit(company.getNit())
                .company(company.getCompany())
                .manager(company.getManager())
                .email(company.getEmail())
                .phone(company.getPhone())
                .address(company.getAddress())
                .department(company.getDepartment())
                .municipality(company.getMunicipality())
                .build();
    }
}

