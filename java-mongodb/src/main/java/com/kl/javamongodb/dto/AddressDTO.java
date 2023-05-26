package com.kl.javamongodb.dto;

import com.kl.javamongodb.entities.Address;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String street;
    private String zipCode;
    private String number;
    private String city;
    private Boolean mainAddress;
    private String personId;

    public AddressDTO(Address entity) {
        this.id = entity.getId();
        this.street = entity.getStreet();
        this.zipCode = entity.getZipCode();
        this.number = entity.getNumber();
        this.city = entity.getCity();
        this.mainAddress = entity.getMainAddress();
        this.personId = entity.getPerson().getId();
    }
}
