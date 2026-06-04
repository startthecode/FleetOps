package org.samtar.warehouse.common.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;

import javax.crypto.SecretKey;

import org.samtar.warehouse.common.dto.JwtClaimsDto;
import org.samtar.warehouse.common.dto.JwtTokenDto;
import org.samtar.warehouse.common.enums.TokenTypes;
import org.samtar.warehouse.common.exceptions.AuthException;
import org.samtar.warehouse.common.exceptions.GenericException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    private final SecretKey accessKey;
    private final SecretKey refreshKey;

    private final Long refreshTokenExpiry;
    private final Long accessTokenExpiry;

    public JwtUtils(
            @Value("${jwt.accessSecret}") String accessKey,
            @Value("${jwt.refreshSecret}") String refreshKey,
            @Value("${jwt.expiration}") Long accessTokenExpiry,
            @Value("${jwt.refreshExpiration}") Long refreshTokenExpiry

    ) {
        this.accessKey = toSecretKey(accessKey);
        this.refreshKey = toSecretKey(refreshKey);
        this.accessTokenExpiry = accessTokenExpiry;
        this.refreshTokenExpiry = refreshTokenExpiry;
    }

    public String generateToken(TokenTypes tokenType, JwtClaimsDto data){
        SecretKey secretKey = TokenTypes.REFRESH_TOKEN == tokenType ? this.refreshKey : this.accessKey;
        long expiry = TokenTypes.REFRESH_TOKEN == tokenType ? this.refreshTokenExpiry : this.accessTokenExpiry;
        Map<String,Object> claims = new HashMap<>();
        claims.put("email",data.email());
        claims.put("username",data.username());
        return Jwts.builder().signWith(secretKey).claims(claims).expiration( new Date(System.currentTimeMillis() + expiry)).issuedAt(new Date()).compact();
    }

    public JwtClaimsDto decodeToken(String token,TokenTypes tokenType) throws Exception{
              SecretKey secretKey = TokenTypes.REFRESH_TOKEN == tokenType ? this.refreshKey : this.accessKey;
              try{
                return parseClaims(token, secretKey);
              }
              catch(ExpiredJwtException ex){
                 throw AuthException.tokenExpired(TokenTypes.ACCESS_TOKEN);
              }
              catch(JwtException ex){
                 throw AuthException.InvalidToken(TokenTypes.ACCESS_TOKEN);
                
              }catch(Exception ex){
                 throw GenericException.exceptionGeneric();
              }
    }


    private JwtClaimsDto parseClaims( String token, SecretKey key){
            Claims data = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        return new JwtClaimsDto(data.get("username",String.class),data.get("email",String.class));
    }


    private SecretKey toSecretKey(String normalKey) {
        byte[] key = HexFormat.of().parseHex(normalKey.trim());
        return Keys.hmacShaKeyFor(key);
    }

}
