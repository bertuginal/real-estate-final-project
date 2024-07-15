package com.example.patika.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtil {

	private static final String SECRET_KEY = "realestate-patika-realy-secret-key-realestate-patika-realy-secret-key";

	private Key key;

	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}

	public Claims getAllClaimsFromToken(String token) {

		// -- @formatter:off --
		return Jwts
				.parserBuilder()
				.setSigningKey(key)			
				.build()
				.parseClaimsJws(token)
				.getBody();
		// -- @formatter:on --

	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	public String getUserName(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDate(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public boolean isTokenExpired(String token) {

		return this.getExpirationDate(token).before(new Date());

	}

	public boolean isValidToken(String token) {
		try {
			return isTokenExpired(token);
		} catch (Exception e) {
			log.error("token is expired");
		}

		return true;
	}

}
