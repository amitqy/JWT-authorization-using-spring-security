package com.loginlogout.demo.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loginlogout.demo.dto.request.LoginRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;


/**
 * This class extends an inbuilt spring security filter and is reponsible for :
 *   - authenticating username and password
 *   - create token after populating it with required claims to be sent to clien
 */
public class UsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public final AuthenticationManager authenticationManager;

    public UsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
//        super(new AntPathRequestMatcher().getPattern("223213"));

        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest authenticationRequest = new ObjectMapper().
                    readValue(request.getInputStream(), LoginRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken
                    (authenticationRequest.getUsername(),
                            authenticationRequest.getPassword());
                  return  authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
                .signWith(Keys.hmacShaKeyFor("secretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecret".getBytes()))
                .compact();

        response.addHeader("Authorization","Bearer " + token);
    }


}
