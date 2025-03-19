package com.user_inventory_manager.user_service.security;

import com.user_inventory_manager.user_service.utils.StandardResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JWTService {

    @Value("${jwt.secret}")
    private String SECRET;    // Stored in the server, confirming that this JWT is provided from the server side

    private static final long VALIDITY  = TimeUnit.HOURS.toMillis(30);

//    Another way of creating a SECRET KEY to generate the signature key. No need since we are giving SECRET KEY in the properties
//    public JWTService(){
//        try {
//            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
//            SecretKey sk = keyGen.generateKey();
//            SECRET = Base64.getEncoder().encodeToString(sk.getEncoded());
//        }catch (NoSuchAlgorithmException e){
//            throw new RuntimeException(e);
//        }
//    }


    public String generateToken(String userName) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("issued by", "inventory-manager");
        return Jwts.builder()
                .claims(claims)
                .subject(userName)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(generateKey())
                .compact();

    }

    private SecretKey generateKey(){
        byte[] decodeKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodeKey);
    }


    public String extractUserName(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    public boolean validateToken(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().after(Date.from(Instant.now()));
    }
}
