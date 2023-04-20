package com.kl.personaddress.services;

import com.kl.personaddress.dto.AddressDTO;
import com.kl.personaddress.entities.Address;
import com.kl.personaddress.entities.Person;
import com.kl.personaddress.repositories.AddressRepository;
import com.kl.personaddress.repositories.PersonRepository;
import com.kl.personaddress.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AddressService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Transactional(readOnly = true)
    public List<AddressDTO> findAll() {
        List<Address> list = addressRepository.findAll();
        return list.stream()
                .map(AddressDTO::new)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<AddressDTO> findAllByPersonId(Long id) {
        Person person = personRepository.getReferenceById(id);
        List<Address> entity = addressRepository.findAllByPerson(person);
        return entity.stream()
                .map(AddressDTO::new)
                .toList();
    }
    @Transactional
    public AddressDTO insert(AddressDTO dto) {
        try {
            Address entity = new Address();
            copyDtoToEntity(dto, entity);

            if (dto.getMainAddress()) {
                entity.setPerson(setTrueMainAddressAsFalse(dto.getPersonId()));
            }
            entity = addressRepository.save(entity);
            return new AddressDTO(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Reference not found");
        }
    }
    @Transactional
    public AddressDTO update(Long id, AddressDTO dto) {
        try {
            Address entity = addressRepository.getReferenceById(id);

            if(dto.getMainAddress() && !entity.getMainAddress()) {
                setTrueMainAddressAsFalse(entity.getPerson().getId());
            }

            copyDtoToEntity(dto, entity);
            return new AddressDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }
    public void delete(Long id) {
        try {
            addressRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }
    private Person setTrueMainAddressAsFalse(Long id) {
        Person personAddress = personRepository.getReferenceById(id);
        personAddress.getAddresses().stream()
                .filter(Address::getMainAddress)
                .forEach(it -> it.setMainAddress(false));
        return personAddress;
    }
    private void copyDtoToEntity(AddressDTO dto, Address entity){
        entity.setStreet(dto.getStreet());
        entity.setZipCode(dto.getZipCode());
        entity.setNumber(dto.getNumber());
        entity.setCity(dto.getCity());
        entity.setMainAddress(dto.getMainAddress());
    }
}
