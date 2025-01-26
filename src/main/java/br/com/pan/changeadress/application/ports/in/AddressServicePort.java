package br.com.pan.changeadress.application.ports.in;

import br.com.pan.changeadress.domain.AddressDomain;

import java.util.List;

public interface AddressServicePort {
    AddressDomain getAddressByZipCode(String zipCode);
    List<String> getStates();
    List<String> getMunicipalitiesByState(String stateId);
}
