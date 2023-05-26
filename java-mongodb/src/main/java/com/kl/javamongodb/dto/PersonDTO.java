package com.kl.javamongodb.dto;

import com.kl.javamongodb.entities.Address;
import com.kl.javamongodb.entities.Person;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
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
