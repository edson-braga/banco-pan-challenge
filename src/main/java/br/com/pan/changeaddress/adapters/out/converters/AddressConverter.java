package br.com.pan.changeaddress.adapters.out.converters;

import br.com.pan.changeaddress.adapters.exceptions.AddressConversionException;
import br.com.pan.changeaddress.domain.AddressDomain;

import java.util.Map;
import java.util.Optional;

public final class AddressConverter {

    private AddressConverter() {}

    public static AddressDomain fromViaCepMap(Optional<Map> map) {
        if (map.isEmpty()) {
            throw new AddressConversionException("Null map received", "NULL_DATA");
        }

        var data = map.get();

        try {
            var zipCode      = (String) data.get("cep");
            var street       = (String) data.get("logradouro");
            var neighborhood = (String) data.get("bairro");
            var city         = (String) data.get("localidade");
            var state        = (String) data.get("uf");

            return new AddressDomain(zipCode, street, neighborhood, city, state);
        } catch (ClassCastException e) {
            throw new AddressConversionException("Error trying to convert AddressDomain", "CONVERSION_ERROR");
        }
    }
}
