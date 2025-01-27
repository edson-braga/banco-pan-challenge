package br.com.pan.changeaddress.application.services;

import br.com.pan.changeaddress.application.ports.out.AddressExternalPort;
import br.com.pan.changeaddress.domain.AddressDomain;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private AddressExternalPort addressExternalPort;

    @InjectMocks
    private AddressService addressService;


    @Test
    void getAddressByZipCodeShouldReturnAddressDomainWhenExternalPortReturnsValidData() {
        var zipCode = "01001000";
        var expectedAddress = new AddressDomain("01001-000", "Praça da Sé", null, null, "Sé", "São Paulo", "SP");

        when(addressExternalPort.fetchAddressByZipCode(zipCode)).thenReturn(expectedAddress);

        var result = addressService.getAddressByZipCode(zipCode);

        assertNotNull(result);
        assertEquals("01001-000", result.zipCode());
        assertEquals("São Paulo", result.city());
        verify(addressExternalPort).fetchAddressByZipCode(zipCode);
    }

    @Test
    void getStatesShouldReturnListOfStatesWhenExternalPortReturnsData() {
        var mockedStates = List.of("São Paulo", "Rio de Janeiro", "Bahia");
        when(addressExternalPort.fetchStates()).thenReturn(mockedStates);

        var result = addressService.getStates();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains("Bahia"));
        verify(addressExternalPort).fetchStates();
    }

    @Test
    void getMunicipalitiesByStateShouldReturnListOfMunicipalitiesWhenExternalPortReturnsData() {
        var stateId = "35";
        var municipalities = List.of("São Paulo", "Campinas", "Santos");
        when(addressExternalPort.fetchMunicipalities(stateId)).thenReturn(municipalities);

        var result = addressService.getMunicipalitiesByState(stateId);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("São Paulo", result.get(0));
        verify(addressExternalPort).fetchMunicipalities(stateId);
    }
}
