package br.com.pan.changeadress.adapters.out.external;

import br.com.pan.changeadress.application.ports.out.AddressExternalPort;
import br.com.pan.changeadress.domain.AddressDomain;
import br.com.pan.changeadress.domain.exceptions.AddressNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class AddressExternalAdapter implements AddressExternalPort {

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
        var url = viaCepBaseUrl + "/" + zipCode + "/json/";
        var response = getForObject(url);

        if (response.isEmpty() || hasError(response)) {
            throw new AddressNotFoundException(
                    "Address not found for ZIP code: " + zipCode,
                    "ADDRESS_NOT_FOUND"
            );
        }

        var map = response.get();
        return new AddressDomain(
                (String) map.get("cep"),
                (String) map.get("logradouro"),
                (String) map.get("bairro"),
                (String) map.get("localidade"),
                (String) map.get("uf"));
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
                .map(m -> (String) m.get("nome"))
                .toList();

        return stateNames.stream()
                .sorted(STATE_COMPARATOR)
                .toList();
    }

    @Override
    public List<String> fetchMunicipalities(String stateId) {
        var url = String.format("%s/estados/%s/municipios", ibgeBaseUrl, stateId);
        var responseArray = getForObjectArray(url);

        // Converte o array em lista, extrai "nome", converte para lista imutável
        return responseArray
                .map(List::of)
                .orElseGet(List::of)
                .stream()
                .map(m -> (String) m.get("nome"))
                .toList();
    }

    private Optional<Map<String, Object>> getForObject(String url) {
        var response = restTemplate.getForObject(url, Map.class);
        return Optional.ofNullable(response);
    }

    private Optional<Map<String, Object>[]> getForObjectArray(String url) {
        var response = restTemplate.getForObject(url, Map[].class);
        return Optional.ofNullable(response);
    }
}
