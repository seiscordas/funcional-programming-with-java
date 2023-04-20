package com.kl.personaddress.repositories;

import com.kl.personaddress.entities.Address;
import com.kl.personaddress.entities.Person;
import com.kl.personaddress.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class AddressRepositoryTests {

    @Autowired
    private AddressRepository repository;
    @Autowired
    private PersonRepository personRepository;

    long existingId;
    long nonExistingId;
    long personIdWithAddress;

    long personIdWithoutAddress = 3L;

    long countTotalAddresses;

    @BeforeEach
    void setUp() throws Exception{
        Long dependentId = 1L;
        existingId = 1L;
        nonExistingId = 1000L;
        personIdWithAddress = 3L;
        personIdWithoutAddress = 1L;
        countTotalAddresses = 5;
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull(){
        Address person = Factory.createAddress();
        person.setId(null);

        person = repository.save(person);
        Assertions.assertNotNull(person.getId());
        Assertions.assertEquals(countTotalAddresses + 1, person.getId());
    }

    @Test
    public void findByIdShouldReturnNonEmptyOptionalAddressWhenIdExists(){
        Optional<Address> result = repository.findById(existingId);
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void findByPersonShouldReturnNonEmptyListAddressWhenExists(){
        Person person = personRepository.getReferenceById(personIdWithAddress);
        List<Address> result = repository.findAllByPerson(person);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void findByPersonShouldReturnEmptyListAddressWhenDoesNotExists(){
        Person person = personRepository.getReferenceById(personIdWithoutAddress);
        List<Address> result = repository.findAllByPerson(person);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void findByIdShouldReturnEmptyOptionalAddressWhenIdDoesNotExists(){
        Optional<Address> result = repository.findById(nonExistingId);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){
        repository.deleteById(existingId);

        Optional<Address> result = repository.findById(existingId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> repository.deleteById(nonExistingId));
    }
}
