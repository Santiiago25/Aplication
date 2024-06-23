package com.swagger.presentation.controller;

import com.swagger.persistence.entity.CompanieEntity;
import com.swagger.persistence.entity.UserEntity;
import com.swagger.presentation.dto.CompanieDto;
import com.swagger.presentation.dto.CompanyDto;
import com.swagger.presentation.dto.UserDto;
import com.swagger.service.interfaces.ICompanieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CompanieController {

    private final ICompanieService companieService;

    // Find All
    @GetMapping("/findAll")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<CompanyDto>> getAllCompanies() {
        List<CompanyDto> companies = companieService.listAll();
        if (companies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    // Find By Id
//    @GetMapping("/find/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN','USER')")
//    public ResponseEntity<ClubDTO> findById(@PathVariable Long id){
//        return new ResponseEntity<>(this.clubService.findById(id), HttpStatus.OK);
//    }

    // Save
    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyDto companyDto) {
        CompanieEntity company = companieService.save(companyDto);
        CompanyDto createdCompanyDto = convertToDto(company);
        return new ResponseEntity<>(createdCompanyDto, HttpStatus.CREATED);
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

    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserEntity> userEntities = companieService.listAllUsers();
        if (userEntities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<UserDto> userDTOs = userEntities.stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getUsername(),
                        user.getName(),
                        user.getLastName(),
                        user.getTypeDocument(),
                        user.getNumDocument(),
                        user.getPhone(),
                        user.getAddress(),
                        user.isEnable(),
                        user.isAccountNoExpired(),
                        user.isAccountNoLocked(),
                        user.isCredentialNoExpired(),
                        user.getStatus(),
                        user.getRoles(),
                        new CompanieDto(user.getCompany().getId(), user.getCompany().getCompany())
                ))
                .collect(Collectors.toList());

        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    // Update
//    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public ResponseEntity<ClubDTO> save(@PathVariable Long id, @RequestBody ClubDTO clubDTO){
//        return new ResponseEntity<>(this.clubService.updateClub(id, clubDTO), HttpStatus.CREATED);
//    }

//    // Delete
//    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public ResponseEntity<String> delete(@PathVariable Long id){
//        return new ResponseEntity<>(this.clubService.deleteClub(id), HttpStatus.OK);
//    }
}
