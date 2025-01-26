package br.com.pan.changeadress.adapters.out.external;

import br.com.pan.changeadress.adapters.exceptions.BadRequestException;
import br.com.pan.changeadress.adapters.exceptions.ExternalApiException;
import br.com.pan.changeadress.adapters.exceptions.MunicipalitiesNotFoundException;
import br.com.pan.changeadress.adapters.out.converters.AddressConverter;
import br.com.pan.changeadress.application.ports.out.AddressExternalPort;
import br.com.pan.changeadress.domain.AddressDomain;
import br.com.pan.changeadress.adapters.exceptions.AddressNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class AddressExternalAdapter implements AddressExternalPort {

    private static final Logger logger = LoggerFactory.getLogger(AddressExternalAdapter.class);

    private final RestTemplate restTemplate;

    @Value("${external.api.via_cep}")
    private String viaCepBaseUrl;

    @Value("${external.api.ibge}")
    private String ibgeBaseUrl;

    private static final Comparator<String> STATE_COMPARATOR = Comparator
            // Define prioridade para São Paulo (0) e Rio de Janeiro (1), demais (2)
            .comparingInt((String s) -> switch (s.toLowerCase()) {
                case "são paulo"      -> 0;
                case "rio de janeiro" -> 1;
                default -> 2;
            })
            // Depois ordena alfabeticamente ignorando maiúsculas/minúsculas
            .thenComparing(String::compareToIgnoreCase);

    public AddressExternalAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public AddressDomain fetchAddressByZipCode(String zipCode) {
        logger.info("Fetching data for zipCode = {}", zipCode);

        var url = viaCepBaseUrl + "/" + zipCode + "/json/";
        var response = getForObject(url);

        if (response.isEmpty() || hasError(response)) {
            throw new AddressNotFoundException(
                    "Address not found for ZIP code: " + zipCode,
                    "ADDRESS_NOT_FOUND"
            );
        }

        return AddressConverter.fromViaCepMap(response);
    }

    private boolean hasError(Optional<Map<String, Object>> response) {
        return response.orElse(Collections.emptyMap()).containsKey("erro");
    }

    @Override
    public List<String> fetchStates() {
        var url = ibgeBaseUrl + "/estados";
        var responseArray = getForObjectArray(url);

        var stateNames = responseArray
                .map(List::of)
                .orElseGet(List::of)
                .stream()
                .map(m -> (String) m.get("name"))
                .toList();

        return stateNames.stream()
                .sorted(STATE_COMPARATOR)
                .toList();
    }

    @Override
    public List<String> fetchMunicipalities(String stateId) {
        logger.info("Fetching data for state id = {}", stateId);

        var url = String.format("%s/estados/%s/municipios", ibgeBaseUrl, stateId);
        var responseArray = getForObjectArray(url);

        var municipalities = responseArray
                .map(List::of)
                .orElseGet(List::of);

        if (municipalities.isEmpty()) {
            throw new MunicipalitiesNotFoundException(
                    "No municipalities found for state: " + stateId,
                    "MUNICIPALITIES_NOT_FOUND"
            );
        }

        return municipalities.stream()
                .map(m -> (String) m.get("name"))
                .toList();
    }

    private Optional<Map<String, Object>> getForObject(String url) {
        try {
            var response = restTemplate.getForObject(url, Map.class);
            return Optional.ofNullable(response);
        } catch (HttpClientErrorException.BadRequest e) {
            throw new BadRequestException(
                    "Bad Request on call " + url + ": " + e.getMessage(),
                    "BAD_REQUEST_ERROR"
            );
        } catch (HttpClientErrorException e) {
            throw new ExternalApiException(
                    "Client error: " + e.getStatusCode() + " on call " + url, String.valueOf(e.getStatusCode()));
        } catch (Exception e) {
            throw new ExternalApiException("Unexpected error when calling " + url, "UNEXPECTED_ERROR");
        }
    }

    private Optional<Map<String, Object>[]> getForObjectArray(String url) {
        try {
            var response = restTemplate.getForObject(url, Map[].class);
            return Optional.ofNullable(response);
        } catch (HttpClientErrorException.BadRequest e) {
            throw new BadRequestException(
                    "Bad Request on call " + url + ": " + e.getMessage(),
                    "BAD_REQUEST_ERROR"
            );
        } catch (HttpClientErrorException e) {
            throw new ExternalApiException(
                    "Client error: " + e.getStatusCode() + " on call " + url, String.valueOf(e.getStatusCode()));
        } catch (Exception e) {
            throw new ExternalApiException("Unexpected error when calling " + url, "UNEXPECTED_ERROR");
        }


    }
}
