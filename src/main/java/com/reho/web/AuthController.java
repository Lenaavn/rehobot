package com.reho.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reho.service.authentication.AuthService;
import com.reho.service.authentication.AuthServiceImpl;
import com.reho.web.models.AuthResponse;
import com.reho.web.models.AuthenticationRequest;
import com.reho.web.models.RegisterRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	@Autowired
	private AuthService authService;

	@Autowired
	private AuthServiceImpl authServiceImpl;

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
	    if (authServiceImpl.existeUsuarioPorEmail(request.getEmail())) {
	        return ResponseEntity.badRequest().body(new AuthResponse("El email es incorrecto, ya existe."));
	    }

	    if (authServiceImpl.existeUsuarioPorTelefono(request.getTelefono())) {
	        return ResponseEntity.badRequest().body(new AuthResponse("El teléfono es incorrecto, ya existe."));
	    }

	    authService.register(request);
	    return ResponseEntity.ok().build();
	}



	@PostMapping("/authenticate")
	public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthenticationRequest request) {
		if (request.getEmail() == null || request.getEmail().isEmpty()) {
			return ResponseEntity.badRequest().body(new AuthResponse("El email es obligatorio."));
		}

		if (request.getContrasena() == null || request.getContrasena().isEmpty()) {
			return ResponseEntity.badRequest().body(new AuthResponse("La contraseña es obligatoria."));
		}

		boolean existeUsuario = authServiceImpl.existeUsuarioPorEmail(request.getEmail());
		if (!existeUsuario) {
			return ResponseEntity.badRequest().body(new AuthResponse("El email es incorrecto."));
		}

		boolean contrasenaCorrecta = authServiceImpl.verificarContrasena(request.getEmail(), request.getContrasena());
		if (!contrasenaCorrecta) {
			return ResponseEntity.badRequest().body(new AuthResponse("La contraseña es incorrecta."));
		}

		AuthResponse response = authService.authenticate(request);
		return ResponseEntity.ok(response);
	}

}
