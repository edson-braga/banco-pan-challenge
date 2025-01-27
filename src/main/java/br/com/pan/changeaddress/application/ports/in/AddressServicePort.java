package br.com.pan.changeaddress.application.ports.in;

import br.com.pan.changeaddress.domain.AddressDomain;

import java.util.List;

public interface AddressServicePort {
    AddressDomain getAddressByZipCode(String zipCode);
    List<String> getStates();
    List<String> getMunicipalitiesByState(String stateId);
}
