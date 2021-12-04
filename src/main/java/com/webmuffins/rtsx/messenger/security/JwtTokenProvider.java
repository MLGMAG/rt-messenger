package com.webmuffins.rtsx.messenger.security;

import com.webmuffins.rtsx.messenger.constants.Role;
import com.webmuffins.rtsx.messenger.exception.JwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.header}")
    private String authorizationHeader;
    @Value("${jwt.body.role-key}")
    private String roleKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            Date expiration = claimsJws.getBody().getExpiration();
            return isFalse(isKeyExpired(expiration));
        } catch (JwtException | IllegalArgumentException ex) {
            throw new JwtAuthenticationException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED);
        }
    }

    private boolean isKeyExpired(Date keyExpiration) {
        Date currentDate = new Date();
        return keyExpiration.before(currentDate);
    }

    public Authentication getAuthentication(String token) {
        UserPrincipal userDetails = getUserPrincipalFromToken(token);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getRole().getAuthorities());
    }

    private UserPrincipal getUserPrincipalFromToken(String token) {
        Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        String email = body.getSubject();
        Role role = getRole(body);
        return new UserPrincipal(email, role);
    }

    private Role getRole(Claims body) {
        String roleValue = (String) body.get(roleKey);
        return Role.getRoleByName(roleValue);
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(authorizationHeader);
    }
}
