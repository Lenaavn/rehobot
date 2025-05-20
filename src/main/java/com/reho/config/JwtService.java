package com.reho.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.reho.persistence.entities.Usuario;
import com.reho.persistence.repository.UsuarioRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

	private static final String SECRET_KEY = "k/XQCmS3xR5VJd23DjTtthpbDuL0mZ2mL6WUxhjkYOA=";
	private final UsuarioRepository usuarioRepository;

	// Generar un nuevo token con datos completos
	public String generateToken(UserDetails userDetails) {
		Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername())
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		Map<String, Object> extraClaims = new HashMap<>();
		extraClaims.put("id", usuario.getId());
		extraClaims.put("nombre", usuario.getNombre());
		extraClaims.put("email", usuario.getEmail());
		extraClaims.put("rol", usuario.getRol());

		return generateToken(extraClaims, userDetails);
	}

	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // Expira en 24 horas
				.signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
	}

	public String getUserName(String token) {
		return getClaim(token, Claims::getSubject);
	}

	private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).setAllowedClockSkewSeconds(60) // Permite hasta 60 segundos de diferencia horaria
				.build().parseClaimsJws(token).getBody();
	}

	private SecretKey getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// Método para verificar si el token está por expirar
	public boolean isTokenExpiringSoon(String token) {
		Date expiration = getExpiration(token);
		long tiempoRestante = expiration.getTime() - System.currentTimeMillis();

		return tiempoRestante < (1000 * 60 * 60); // Se considera "pronto" si queda menos de 1 hora
	}

	// Método para renovar el token automáticamente si está por expirar
	public String renovarTokenSiEsNecesario(String token, UserDetails userDetails) {
		if (isTokenExpiringSoon(token)) {
			return generateToken(userDetails); // Genera un nuevo token si está por expirar
		}
		return token;
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String userName = getUserName(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return getExpiration(token).before(new Date());
	}

	private Date getExpiration(String token) {
		return getClaim(token, Claims::getExpiration);
	}
}