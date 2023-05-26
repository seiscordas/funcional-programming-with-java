package com.kl.javamongodb.services.interfaces;

import com.kl.javamongodb.dto.AddressDTO;
import com.kl.javamongodb.entities.Address;
import com.kl.javamongodb.entities.Person;

import java.util.List;

public interface IAddressService {
    List<AddressDTO> findAll();
    List<AddressDTO> findAllByPersonId(String id);
    AddressDTO insert(AddressDTO dto);
    AddressDTO update(String id, AddressDTO dto);
    void delete(String id);
    Person setTrueMainAddressAsFalse(String id);
    void copyDtoToEntity(AddressDTO dto, Address entity);
}
