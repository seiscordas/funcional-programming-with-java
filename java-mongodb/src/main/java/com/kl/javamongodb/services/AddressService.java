package com.kl.javamongodb.services;

import com.kl.javamongodb.dto.AddressDTO;
import com.kl.javamongodb.entities.Address;
import com.kl.javamongodb.entities.Person;
import com.kl.javamongodb.repositories.AddressRepository;
import com.kl.javamongodb.repositories.PersonRepository;
import com.kl.javamongodb.services.exceptions.ResourceNotFoundException;
import com.kl.javamongodb.services.interfaces.IAddressService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AddressService implements IAddressService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Transactional(readOnly = true)
    @Override
    public List<AddressDTO> findAll() {
        List<Address> list = addressRepository.findAll();
        return list.stream()
                .map(AddressDTO::new)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    @Override
    public List<AddressDTO> findAllByPersonId(String id) {
        Person person = personRepository.findById(id).orElseThrow();
        List<Address> entity = addressRepository.findAllByPerson(person);
        return entity.stream()
                .map(AddressDTO::new)
                .toList();
    }
    @Transactional
    @Override
    public AddressDTO insert(AddressDTO dto) {
        try {
            Address entity = new Address();
            copyDtoToEntity(dto, entity);

            if (dto.getMainAddress()) {
                entity.setPerson(setTrueMainAddressAsFalse(dto.getPersonId()));
            }
            entity = addressRepository.save(entity);
            return new AddressDTO(entity);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Reference not found");
        }
    }
    @Transactional
    @Override
    public AddressDTO update(String id, AddressDTO dto) {
        try {
            Address entity = addressRepository.findById(id).orElseThrow();

            if(dto.getMainAddress() && !entity.getMainAddress()) {
                setTrueMainAddressAsFalse(entity.getPerson().getId());
            }

            copyDtoToEntity(dto, entity);
            return new AddressDTO(entity);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }
    @Override
    public void delete(String id) {
        try {
            addressRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }
    @Override
    public Person setTrueMainAddressAsFalse(String id) {
        Person person = personRepository.findById(id).orElseThrow();
        person.getAddresses().stream()
                .filter(Address::getMainAddress)
                .forEach(it -> it.setMainAddress(false));
        return person;
    }
    @Override
    public void copyDtoToEntity(AddressDTO dto, Address entity){
        entity.setStreet(dto.getStreet());
        entity.setZipCode(dto.getZipCode());
        entity.setNumber(dto.getNumber());
        entity.setCity(dto.getCity());
        entity.setMainAddress(dto.getMainAddress());
    }
}
