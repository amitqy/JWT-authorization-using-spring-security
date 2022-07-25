package com.loginlogout.demo.service;

import com.loginlogout.demo.dto.request.LoginRequest;
import com.loginlogout.demo.entity.Person;

import java.util.List;

public interface PersonService {

    Person createPerson(Person person);

    List<Person> getPersons();

    List<Person> authenticate(LoginRequest loginRequest) throws Exception;
}
