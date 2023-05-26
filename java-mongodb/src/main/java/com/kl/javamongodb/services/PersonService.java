package com.kl.javamongodb.services;

import com.kl.javamongodb.dto.PersonDTO;
import com.kl.javamongodb.entities.Person;
import com.kl.javamongodb.repositories.PersonRepository;
import com.kl.javamongodb.services.exceptions.DatabaseException;
import com.kl.javamongodb.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonService {
    @Autowired
    private PersonRepository repository;

    @Transactional(readOnly = true)
    public List<PersonDTO> findAll() {
        List<Person> list = repository.findAll();
        return list.stream().map(PersonDTO::new).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public PersonDTO findById(String id) {
        Optional<Person> obj = repository.findById(id);
        Person entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
        return new PersonDTO(entity, entity.getAddresses());
    }
    @Transactional
    public PersonDTO insert(PersonDTO personDTO) {
        Person entity = new Person(personDTO);
        entity = repository.save(entity);
        return new PersonDTO(entity);
    }
    @Transactional
    public PersonDTO update(String id, PersonDTO dto) {
        try {
            Person entity = repository.findById(id).orElseThrow();
            entity.setName(dto.getName());
            entity.setBirthDate(dto.getBirthDate());
            return new PersonDTO(entity);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }
    public void delete(String id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation database");
        }
    }
}
