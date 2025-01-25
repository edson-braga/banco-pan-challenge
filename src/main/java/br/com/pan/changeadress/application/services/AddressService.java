package br.com.pan.changeadress.application.services;

import br.com.pan.changeadress.application.ports.in.AddressServicePort;
import br.com.pan.changeadress.application.ports.out.AddressExternalPort;
import br.com.pan.changeadress.domain.AddressDomain;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService implements AddressServicePort {

    private final AddressExternalPort addressExternalPort;

    public AddressService(AddressExternalPort addressExternalPort) {
        this.addressExternalPort = addressExternalPort;
    }

    @Override
    public AddressDomain getAddressByZipCode(String zipCode) {
        return addressExternalPort.fetchAddressByZipCode(zipCode);
    }

    @Override
    public List<String> getStates() {
        return addressExternalPort.fetchStates();
    }

    @Override
    public List<String> getMunicipalitiesByState(String stateId) {
        return addressExternalPort.fetchMunicipalities(stateId);
    }
}
