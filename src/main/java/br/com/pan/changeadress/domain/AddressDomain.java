package br.com.pan.changeadress.domain;

import br.com.pan.changeadress.domain.exceptions.AddressValidationException;

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
            throw new AddressValidationException("Campo 'zipCode' não pode ser vazio");
        }
        if (street == null || street.isBlank()) {
            throw new AddressValidationException("Campo 'street' não pode ser vazio");
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