package com.kl.personaddress.repositories;

import com.kl.personaddress.entities.Person;
import com.kl.personaddress.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class PersonRepositoryTests {

    @Autowired
    private PersonRepository repository;

    long existingId;
    long nonExistingId;
    Long dependentId;
    long countTotalPersons;

    @BeforeEach
    void setUp() throws Exception{
        existingId = 1L;
        nonExistingId = 1000L;
        dependentId = 3L;
        countTotalPersons = 3;
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull(){
        Person person = Factory.createPerson();
        person.setId(null);

        person = repository.save(person);
        Assertions.assertNotNull(person.getId());
        Assertions.assertEquals(countTotalPersons + 1, person.getId());
    }

    @Test
    public void findByIdShouldReturnNonEmptyOptionalPersonWhenIdExists(){
        Optional<Person> result = repository.findById(existingId);
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void findByIdShouldReturnEmptyOptionalPersonWhenIdDoesNotExists(){
        Optional<Person> result = repository.findById(nonExistingId);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){
        repository.deleteById(existingId);

        Optional<Person> result = repository.findById(existingId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> repository.deleteById(nonExistingId));
    }
}
