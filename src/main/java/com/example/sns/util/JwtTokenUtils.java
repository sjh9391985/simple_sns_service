package com.example.sns.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtTokenUtils {

    public static String generateToken(String username, String key, long expiredTimeMs) {

        Claims claims = Jwts.claims();
        claims.put("username", username);
        return Jwts.builder()
                .setClaims(claims) // 생성된 토큰
                .setIssuedAt(new Date(System.currentTimeMillis())) // 토큰 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs)) // 토큰 만료 시간
                .signWith(getKey(key), SignatureAlgorithm.HS256) // 알고리즘에 따라 키 바이트 변환
                .compact();
    }

    private static Key getKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
