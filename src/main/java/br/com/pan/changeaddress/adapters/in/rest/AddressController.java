package br.com.pan.changeaddress.adapters.in.rest;

import br.com.pan.changeaddress.application.ports.in.AddressServicePort;
import br.com.pan.changeaddress.domain.AddressDomain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class AddressController {

    private final AddressServicePort addressService;

    public AddressController(AddressServicePort addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/{zipCode}")
    public AddressDomain getAddressByZipCode(@PathVariable String zipCode) {
        return addressService.getAddressByZipCode(zipCode);
    }

    @GetMapping("/estados")
    public List<String> getStates() {
        return addressService.getStates();
    }

    @GetMapping("/estados/{stateId}/municipios")
    public List<String> getMunicipalitiesByState(@PathVariable String stateId) {
        return addressService.getMunicipalitiesByState(stateId);
    }
}
