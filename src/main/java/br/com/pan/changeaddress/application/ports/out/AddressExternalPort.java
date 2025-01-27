package br.com.pan.changeaddress.application.ports.out;

import br.com.pan.changeaddress.domain.AddressDomain;

import java.util.List;

public interface AddressExternalPort {
    AddressDomain fetchAddressByZipCode(String zipCode);
    List<String> fetchStates();
    List<String> fetchMunicipalities(String stateId);
}
