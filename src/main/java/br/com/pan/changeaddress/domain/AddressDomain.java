package br.com.pan.changeaddress.domain;

import br.com.pan.changeaddress.domain.exceptions.AddressValidationException;

public record AddressDomain(
        String zipCode,
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state
) {

    public AddressDomain {
        if (zipCode == null || zipCode.isBlank()) {
            throw new AddressValidationException("field 'zipCode' cannot be empty");
        }
        if (street == null || street.isBlank()) {
            throw new AddressValidationException("field 'street' cannot be empty");
        }
    }

    public AddressDomain(
            String zipCode,
            String street,
            String neighborhood,
            String city,
            String state
    ) {
        this(zipCode, street, null, null, neighborhood, city, state);
    }
}