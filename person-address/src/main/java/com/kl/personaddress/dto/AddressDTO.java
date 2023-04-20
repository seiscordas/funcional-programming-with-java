package com.kl.personaddress.dto;

import com.kl.personaddress.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class AddressDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String street;
    private String zipCode;
    private String number;
    private String city;
    private Boolean mainAddress;
    private Long personId;

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
