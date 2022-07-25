package com.loginlogout.demo.service.impl;

import com.loginlogout.demo.dto.request.LoginRequest;
import com.loginlogout.demo.entity.Person;
import com.loginlogout.demo.repo.PersonRepo;
import com.loginlogout.demo.service.PersonService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DaoAuthenticationProvider authenticationProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Person createPerson( Person person) {
        log.info("person to create is: {}", person);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        return personRepo.save(person);
    }

    @Override
    public List<Person> getPersons() {
       return personRepo.findAll();
    }

    @Override
    public List<Person> authenticate(LoginRequest loginRequest)  {
        Authentication authObject =  null;
        try {
            log.info("login info {}", loginRequest.getPassword());
            authObject =  authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authObject);
            return personRepo.findAll();
        } catch (BadCredentialsException e) {
            log.info("Bad credentials " );
            throw new BadCredentialsException("bad");
        }

    }
}
