package com.loginlogout.demo.controller;

import com.loginlogout.demo.dto.request.LoginRequest;
import com.loginlogout.demo.entity.Person;
import com.loginlogout.demo.service.PersonService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/persons")
@Log4j2
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/register")
    public Person registerPerson(@RequestBody Person person){
        return personService.createPerson(person);
    }

    @GetMapping
    public List<Person> getPersons(){
       return personService.getPersons();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        log.info("here");
     return ResponseEntity.ok(personService.getPersons());
    }

}
