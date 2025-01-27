package br.com.pan.changeaddress.adapters.out.external;

import br.com.pan.changeaddress.adapters.exceptions.AddressNotFoundException;
import br.com.pan.changeaddress.adapters.exceptions.BadRequestException;
import br.com.pan.changeaddress.adapters.exceptions.ExternalApiException;
import br.com.pan.changeaddress.adapters.exceptions.MunicipalitiesNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link AddressExternalAdapter}.
 */
@ExtendWith(MockitoExtension.class)
class AddressExternalAdapterTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AddressExternalAdapter adapter;

    private final String viaCepBaseUrl = "https://viacep.com.br/ws";
    private final String ibgeBaseUrl = "https://servicodados.ibge.gov.br/api/v1/localidades";

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(adapter, "viaCepBaseUrl", "https://viacep.com.br/ws");
        ReflectionTestUtils.setField(adapter, "ibgeBaseUrl", "https://servicodados.ibge.gov.br/api/v1/localidades");
    }

    @Test
    void fetchAddressByZipCodeWhenSuccessShouldReturnAddressDomain() {
        var zipCode = "01001000";
        var url = this.viaCepBaseUrl + "/" + zipCode + "/json/";

        var mockedResponse = Map.of(
                "cep", "01001-000",
                "logradouro", "Praça da Sé",
                "bairro", "Sé",
                "localidade", "São Paulo",
                "uf", "SP"
        );
        when(restTemplate.getForObject(eq(url), eq(Map.class)))
                .thenReturn(mockedResponse);

        var result = adapter.fetchAddressByZipCode(zipCode);

        assertNotNull(result);
        assertEquals("01001-000", result.zipCode());
        assertEquals("Praça da Sé", result.street());
        assertEquals("Sé", result.neighborhood());
        assertEquals("São Paulo", result.city());
        assertEquals("SP", result.state());
    }

    @Test
    void fetchAddressByZipCodeWhenNotFoundShouldThrowAddressNotFoundException() {
        var zipCode = "99999999";
        var url = this.viaCepBaseUrl + "/" + zipCode + "/json/";

        var mockedResponse = Map.of("erro", true);
        when(restTemplate.getForObject(eq(url), eq(Map.class)))
                .thenReturn(mockedResponse);

        var ex = assertThrows(AddressNotFoundException.class,
                () -> adapter.fetchAddressByZipCode(zipCode));
        assertEquals("ADDRESS_NOT_FOUND", ex.getErrorCode());
    }

    @Test
    void fetchAddressByZipCodeWhenHttpClientErrorExceptionBadRequestShouldThrowBadRequestException() {
        var zipCode = "00000000";
        var url = this.viaCepBaseUrl + "/" + zipCode + "/json/";

        when(restTemplate.getForObject(eq(url), eq(Map.class)))
                .thenThrow(HttpClientErrorException.BadRequest.class);

        var ex = assertThrows(BadRequestException.class,
                () -> adapter.fetchAddressByZipCode(zipCode));
        assertEquals("BAD_REQUEST_ERROR", ex.getErrorCode());
    }

    @Test
    void fetchStatesWhenSuccessShouldReturnOrderedList() {
        var url = this.ibgeBaseUrl + "/estados";
        var statesMock = new Map[] {
                Map.of("nome", "Bahia"),
                Map.of("nome", "São Paulo"),
                Map.of("nome", "rio de janeiro"),
                Map.of("nome", "Acre")
        };
        when(restTemplate.getForObject(eq(url), eq(Map[].class)))
                .thenReturn(statesMock);

        var result = adapter.fetchStates();

        assertEquals(4, result.size());
        assertEquals("São Paulo", result.get(0));
        assertEquals("rio de janeiro", result.get(1));
        assertEquals("Acre", result.get(2));
        assertEquals("Bahia", result.get(3));
    }

    @Test
    void fetchStatesWhenRestTemplateReturnsNullShouldReturnEmptyList() {
        var url = this.ibgeBaseUrl + "/estados";
        when(restTemplate.getForObject(eq(url), eq(Map[].class)))
                .thenReturn(null);

        var result = adapter.fetchStates();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void fetchMunicipalitiesWhenEmptyShouldThrowMunicipalitiesNotFoundException() {
        var stateId = "35";
        var url = String.format("%s/estados/%s/municipios", this.ibgeBaseUrl, stateId);

        when(restTemplate.getForObject(eq(url), eq(Map[].class)))
                .thenReturn(new Map[]{}); // empty array

        var ex = assertThrows(MunicipalitiesNotFoundException.class,
                () -> adapter.fetchMunicipalities(stateId));
        assertEquals("MUNICIPALITIES_NOT_FOUND", ex.getErrorCode());
    }

    @Test
    void fetchMunicipalitiesWhenSuccessShouldReturnList() {
        var stateId = "35";
        var url = String.format("%s/estados/%s/municipios", this.ibgeBaseUrl, stateId);

        var municipalitiesMock = new Map[]{
                Map.of("nome", "São Paulo"),
                Map.of("nome", "Campinas")
        };
        when(restTemplate.getForObject(eq(url), eq(Map[].class)))
                .thenReturn(municipalitiesMock);

        var result = adapter.fetchMunicipalities(stateId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("São Paulo", result.get(0));
        assertEquals("Campinas", result.get(1));
    }

    @Test
    void fetchMunicipalitiesWhenHttpClientErrorShouldThrowExternalApiException() {
        var stateId = "35";
        var url = String.format("%s/estados/%s/municipios", this.ibgeBaseUrl, stateId);

        when(restTemplate.getForObject(eq(url), eq(Map[].class)))
                .thenThrow(HttpClientErrorException.class);

        var ex = assertThrows(ExternalApiException.class,
                () -> adapter.fetchMunicipalities(stateId));
        assertFalse(ex.getErrorCode().isEmpty());
    }

    @Test
    void fetchAddressByZipCodeWhenUnexpectedExceptionShouldThrowExternalApiException() {
        var zipCode = "unknown";
        var url = this.viaCepBaseUrl + "/" + zipCode + "/json/";

        when(restTemplate.getForObject(eq(url), eq(Map.class)))
                .thenThrow(new RuntimeException("Something unexpected"));

        var ex = assertThrows(ExternalApiException.class,
                () -> adapter.fetchAddressByZipCode(zipCode));
        assertEquals("UNEXPECTED_ERROR", ex.getErrorCode());
    }
}
