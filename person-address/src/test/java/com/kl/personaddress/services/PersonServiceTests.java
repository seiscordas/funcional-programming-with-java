package com.kl.personaddress.services;

import com.kl.personaddress.dto.PersonDTO;
import com.kl.personaddress.entities.Person;
import com.kl.personaddress.repositories.PersonRepository;
import com.kl.personaddress.services.exceptions.DatabaseException;
import com.kl.personaddress.services.exceptions.ResourceNotFoundException;
import com.kl.personaddress.tests.Factory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class PersonServiceTests {
    @Mock
    private PersonRepository personRepository;
    @InjectMocks
    private PersonService service;
    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private PersonDTO personDTO;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        dependentId = 3L;
        Person person = Factory.createPerson();
        personDTO = Factory.createPersonDTO();

        Mockito.when(personRepository.save(any())).thenReturn(person);
        Mockito.when(personRepository.findById(existingId)).thenReturn(Optional.of(person));
        Mockito.when(personRepository.getReferenceById(existingId)).thenReturn(person);
        Mockito.when(personRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
        Mockito.doNothing().when(personRepository).deleteById(existingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(personRepository).deleteById(nonExistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(personRepository).deleteById(dependentId);
    }

    @Test
    public void insertShouldReturnPersonDTOWhenCreatePerson() {
        PersonDTO result = service.insert(personDTO);
        Assertions.assertNotNull(result);
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistingId, personDTO);
        });
    }

    @Test
    public void updateShouldReturnPersonDTOWhenIdExists() {
        PersonDTO result = service.update(personDTO.getId(), personDTO);
        Assertions.assertNotNull(result);
    }

    @Test
    public void findByIdShouldThrowExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });
    }

    @Test
    public void findByIdShouldReturnPersonDTOWhenIdExists() {
        PersonDTO result = service.findById(existingId);
        Assertions.assertNotNull(result);
    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
        Assertions.assertThrows(DatabaseException.class, () -> {
            service.delete(dependentId);
        });
        Mockito.verify(personRepository, Mockito.times(1)).deleteById(dependentId);
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
        Mockito.verify(personRepository, Mockito.times(1)).deleteById(nonExistingId);
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });
        Mockito.verify(personRepository, Mockito.times(1)).deleteById(existingId);
    }
}
