package com.kl.personaddress.dto;

import com.kl.personaddress.entities.Address;
import com.kl.personaddress.entities.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class PersonDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private LocalDate birthDate;

    private List<AddressDTO> addresses = new ArrayList<>();

    public PersonDTO(Person entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.birthDate = entity.getBirthDate();
    }

    public PersonDTO(Person entity, List<Address> addresses) {
        this(entity);
        addresses.forEach(address -> this.addresses.add(new AddressDTO(address)));
    }
}
