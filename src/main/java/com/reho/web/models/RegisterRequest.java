package com.reho.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
	
    private String nombre;
    private String apellidos;
    private String email;
    private String contrasena;
    private String telefono;

}
