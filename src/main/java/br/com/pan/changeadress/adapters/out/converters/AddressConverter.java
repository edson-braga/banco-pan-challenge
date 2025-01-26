package br.com.pan.changeadress.adapters.out.converters;

import br.com.pan.changeadress.adapters.exceptions.AddressConversionException;
import br.com.pan.changeadress.domain.AddressDomain;

import java.util.Map;
import java.util.Optional;

public final class AddressConverter {

    private AddressConverter() {}

    public static AddressDomain fromViaCepMap(Optional<Map<String, Object>> map) {
        if (map.isEmpty()) {
            throw new AddressConversionException("Map de endereço ViaCEP é nulo", "NULL_DATA");
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
            throw new AddressConversionException("Erro de cast ao ler campos de endereço via CEP", "CONVERSION_ERROR");
        }
    }
}
