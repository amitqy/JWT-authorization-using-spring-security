package com.loginlogout.demo.security;

import com.loginlogout.demo.entity.Person;
import com.loginlogout.demo.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class PersonDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PersonRepo personRepo;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Person> personOptional = personRepo.findByUsername(userName);
        if(!personOptional.isPresent()){
            throw new UsernameNotFoundException("Person: " + userName + "was not found" );
        }
        return new PersonDetailsImpl(personOptional.get());
    }
}
