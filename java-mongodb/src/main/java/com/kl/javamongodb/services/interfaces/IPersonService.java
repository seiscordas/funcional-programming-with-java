package com.kl.javamongodb.services.interfaces;

import com.kl.javamongodb.dto.PersonDTO;

import java.util.List;

public interface IPersonService{

    List<PersonDTO> findAll();
    PersonDTO findById(Long id);
    PersonDTO insert(PersonDTO personDTO);
    PersonDTO update(Long id, PersonDTO dto);
    void delete(String id);
}
