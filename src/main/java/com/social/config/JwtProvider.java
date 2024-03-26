package com.social.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {

    //	private static SecretKey key = Keys.hmacShaKeyFor(JwtConstants.SECRETE_KEY.getBytes());

	// Generate a secure key with the required size (256 bits)
	private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public static String generateToken(Authentication auth) {

		String jwt = Jwts.builder()
				.setIssuer("Anuragsisodiya")
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + 8460000))
				.claim("email", auth.getName())
				.signWith(key)
				.compact();
		return jwt;

	}

	public static String getEmailFromJwtToken(String jwt) {

		// Bearer token
		jwt = jwt.substring(7);

		Claims claims = Jwts.parser()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(jwt)
				.getBody();

		String email = String.valueOf(claims.get("email"));
		return email;
	}
}
