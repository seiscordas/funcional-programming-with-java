package com.kl.personaddress.services;

import com.kl.personaddress.dto.AddressDTO;
import com.kl.personaddress.entities.Address;
import com.kl.personaddress.entities.Person;
import com.kl.personaddress.repositories.AddressRepository;
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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class AddressServiceTests {
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private PersonRepository personRepository;
    @InjectMocks
    private AddressService service;
    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private AddressDTO addressDTO;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        dependentId = 3L;
        Person person = Factory.createPerson();
        Address address = Factory.createAddress();
        addressDTO = Factory.createAddressDTO();
        List<Address> addressList = new ArrayList<>();

        Mockito.when(addressRepository.save(any())).thenReturn(address);

        Mockito.when(addressRepository.findById(existingId)).thenReturn(Optional.of(address));

        Mockito.when(addressRepository.findAllByPerson(person)).thenReturn(addressList);
        Mockito.when(addressRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        Mockito.when(personRepository.getReferenceById(existingId)).thenReturn(person);
        Mockito.when(personRepository.getReferenceById(existingId)).thenReturn(person);
        Mockito.when(personRepository.getReferenceById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
        Mockito.when(addressRepository.getReferenceById(existingId)).thenReturn(address);

        Mockito.doNothing().when(addressRepository).deleteById(existingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(addressRepository).deleteById(nonExistingId);
        Mockito.doThrow(DatabaseException.class).when(addressRepository).deleteById(dependentId);
    }

    @Test
    public void findAllByPersonShouldThrowExceptionWhenPersonDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findAllByPersonId(nonExistingId));
    }

    @Test
    public void findAllByPersonShouldReturnAllByPerson() {
        List<AddressDTO> result = service.findAllByPersonId(dependentId);
        Assertions.assertNotNull(result);
    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
        Assertions.assertThrows(DatabaseException.class, () -> service.delete(dependentId));
        Mockito.verify(addressRepository, Mockito.times(1)).deleteById(dependentId);
    }

    @Test
    public void updateShouldReturnAddressDTOWhenIdExists() {
        AddressDTO result = service.update(addressDTO.getId(), addressDTO);
        Assertions.assertNotNull(result);
    }

    @Test
    public void insertShouldReturnAddressDTOWhenCreateAddress() {
        AddressDTO result = service.insert(addressDTO);
        Assertions.assertNotNull(result);
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.update(nonExistingId, addressDTO));
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.delete(nonExistingId));
        Mockito.verify(addressRepository, Mockito.times(1)).deleteById(nonExistingId);
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> service.delete(existingId));
        Mockito.verify(addressRepository, Mockito.times(1)).deleteById(existingId);
    }
}
