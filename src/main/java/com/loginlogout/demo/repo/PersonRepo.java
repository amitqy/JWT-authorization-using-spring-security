package com.loginlogout.demo.repo;

import com.loginlogout.demo.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepo extends JpaRepository<Person,Long> {
    Optional<Person> findByUsername(String username);
}
