package br.com.pan.changeadress.application.ports.out;

import br.com.pan.changeadress.domain.AddressDomain;

import java.util.List;

public interface AddressExternalPort {
    AddressDomain fetchAddressByZipCode(String zipCode);
    List<String> fetchStates();
    List<String> fetchMunicipalities(String stateId);
}
