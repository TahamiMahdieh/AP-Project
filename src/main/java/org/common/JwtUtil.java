package org.common;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = "mahraam";
    private static final long EXPIRATION_DATE = 3600000; // one hour in milliseconds

    public static String generateToken(String email) {
        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        return token;
    }

    public static Claims validateToken(String token) {
        Claims claim = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claim;
    }

    public static boolean isTokenExpired(String token) {
        boolean bool = validateToken(token).getExpiration().before(new Date());
        return bool;
    }
}
