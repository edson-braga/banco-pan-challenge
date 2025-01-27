package br.com.pan.changeaddress.adapters.out.external;

import br.com.pan.changeaddress.adapters.exceptions.BadRequestException;
import br.com.pan.changeaddress.adapters.exceptions.ExternalApiException;
import br.com.pan.changeaddress.adapters.exceptions.MunicipalitiesNotFoundException;
import br.com.pan.changeaddress.adapters.out.converters.AddressConverter;
import br.com.pan.changeaddress.adapters.out.external.utils.StateComparator;
import br.com.pan.changeaddress.application.ports.out.AddressExternalPort;
import br.com.pan.changeaddress.domain.AddressDomain;
import br.com.pan.changeaddress.adapters.exceptions.AddressNotFoundException;
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

    public AddressExternalAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public AddressDomain fetchAddressByZipCode(String zipCode) {
        logger.info("Fetching address for zipCode = {}", zipCode);

        var url = viaCepBaseUrl + "/" + zipCode + "/json/";
        var responseOpt = getForObjectGeneric(url, Map.class);

        if (responseOpt.isEmpty() || hasError(responseOpt.get())) {
            throw new AddressNotFoundException(
                    "Address not found for ZIP code: " + zipCode,
                    "ADDRESS_NOT_FOUND"
            );
        }

        return AddressConverter.fromViaCepMap(responseOpt);
    }

    @Override
    public List<String> fetchStates() {
        logger.info("Fetching all states from IBGE");

        var url = ibgeBaseUrl + "/estados";
        var responseArrayOpt = getForObjectGeneric(url, Map[].class);

        var states = responseArrayOpt
                .map(List::of)
                .orElseGet(List::of);

        var stateNames = states.stream()
                .map(m -> (String) m.get("nome"))
                .toList();

        return stateNames.stream()
                .sorted(StateComparator.PRIORITY_THEN_ALPHABETIC)
                .toList();
    }

    @Override
    public List<String> fetchMunicipalities(String stateId) {
        logger.info("Fetching municipalities for stateId = {}", stateId);

        var url = String.format("%s/estados/%s/municipios", ibgeBaseUrl, stateId);
        var responseArrayOpt = getForObjectGeneric(url, Map[].class);

        var municipalities = responseArrayOpt
                .map(List::of)
                .orElseGet(List::of);

        if (municipalities.isEmpty()) {
            throw new MunicipalitiesNotFoundException(
                    "No municipalities found for state: " + stateId,
                    "MUNICIPALITIES_NOT_FOUND"
            );
        }

        return municipalities.stream()
                .map(m -> (String) m.get("nome"))
                .toList();
    }

    private <T> Optional<T> getForObjectGeneric(String url, Class<T> responseType) {
        try {
            var response = restTemplate.getForObject(url, responseType);
            return Optional.ofNullable(response);
        } catch (HttpClientErrorException.BadRequest e) {
            throw new BadRequestException(
                    "Bad Request on call " + url + ": " + e.getMessage(),
                    "BAD_REQUEST_ERROR"
            );
        } catch (HttpClientErrorException e) {
            throw new ExternalApiException(
                    "Client error: " + e.getStatusCode() + " on call " + url,
                    String.valueOf(e.getStatusCode())
            );
        } catch (Exception e) {
            throw new ExternalApiException(
                    "Unexpected error calling " + url,
                    "UNEXPECTED_ERROR"
            );
        }
    }

    private boolean hasError(Map<String, Object> response) {
        return Boolean.TRUE.equals(response.get("erro"));
    }
}
