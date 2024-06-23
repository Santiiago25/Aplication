package com.swagger.presentation.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor //genera los contructores
@NoArgsConstructor //genera contructor vacio
@ToString
@Builder //nos permite enviar datos en vez de usar los constructores
public class CompanyDto {

    private Long id;

    @Size(min = 5, max = 10)
    @NotEmpty(message = "Nit requerido")
    private int nit;

    @Size(min = 5, max = 40)
    @NotEmpty(message = "Empresa requerida")
    private String company;

    //definiendo el tamaño maximo del nombre
    @NotEmpty(message = "Nombre del manager requerido")
    private String manager;

    @NotEmpty(message = "Correo requerido!")
    private String email;

    @Size(min = 10, max = 12)
    @NotEmpty(message = "Correo requerido!")
    private String phone;

    private String address;

    @NotEmpty(message = "Departamento requerido")
    private String department;

    @NotEmpty(message = "Municipio requerido")
    private String municipality;

}
