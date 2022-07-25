package com.loginlogout.demo.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Each request coming to be server has to pass through
 * this filter ( only once ). The responsibility of this class is to
 * verify the token that comes with each request from client
 */
public class JwtVerifier extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        if(Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        String token = authorizationHeader.replace("Bearer ","");
        try{
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor("secretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecret".getBytes()))
                    .build().parseClaimsJws(token);
            String username = claimsJws.getBody().getSubject();
            Authentication authentication = new UsernamePasswordAuthenticationToken(username,null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (JwtException e){
            throw new IllegalStateException(String.format("Token %s can't be trusted", token));
        }
        // pass request response to next filter in filter chain
       filterChain.doFilter(request,response);
    }
}
