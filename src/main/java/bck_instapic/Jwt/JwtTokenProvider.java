package bck_instapic.Jwt;

import bck_instapic.user.userDetail.CustomUserDetailsService;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenProvider {
    private final long EXPIRATION_TIME = 86400000L * 30;

    private final JwtBuilder jwtBuilder;
    private final JwtParser jwtParser;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtTokenProvider(JwtBuilder jwtBuilder, JwtParser jwtParser, CustomUserDetailsService customUserDetailsService) {
        this.jwtBuilder = jwtBuilder;
        this.jwtParser = jwtParser;
        this.customUserDetailsService = customUserDetailsService;
    }

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return jwtBuilder
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .compact();
    }

    public String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return jwtBuilder
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        String username = getUsernameFromToken(token);
        UserDetails userDetails = loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
    }

    public String getUsernameFromToken(String token) {
        return jwtParser
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Invalid jwtToken
            return false;
        }
    }

    private UserDetails loadUserByUsername(String username) {
        return Optional.of(username)
                .map(customUserDetailsService::loadUserByUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
