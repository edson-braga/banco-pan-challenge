package br.com.pan.changeadress.domain;

public record AddressDomain(
        String zipCode,
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state
) {
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