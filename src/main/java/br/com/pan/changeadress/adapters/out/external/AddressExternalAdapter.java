package br.com.pan.changeadress.adapters.out.external;

import br.com.pan.changeadress.application.ports.out.AddressExternalPort;
import br.com.pan.changeadress.domain.AddressDomain;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AddressExternalAdapter implements AddressExternalPort {

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
        String url = viaCepBaseUrl + "/" + zipCode + "/json/";
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null) {
            return null;
        }

        AddressDomain address = new AddressDomain();
        address.setZipCode((String) response.get("cep"));
        address.setStreet((String) response.get("logradouro"));
        address.setNeighborhood((String) response.get("bairro"));
        address.setCity((String) response.get("localidade"));
        address.setState((String) response.get("uf"));
        return address;
    }

    @Override
    public List<String> fetchStates() {
        var url = ibgeBaseUrl + "/estados";
        // O restTemplate retorna um array de Maps; transformamos em List via List.of(...)
        var statesArray = restTemplate.getForObject(url, Map[].class);
        if (statesArray == null) return List.of();

        var states = List.of(statesArray); // Java 9+ (List.of) ou Arrays.asList(...)

        // Extrai apenas o campo "nome"
        var stateNames = states.stream()
                .map(e -> (String) e.get("nome"))
                .toList(); // toList() do Java 16+ (retorna List imutável)

        // Ordenamos com base em:
        //   1) São Paulo (0)
        //   2) Rio de Janeiro (1)
        //   3) Demais estados (2), e então ordem alfabética
        var customComparator = Comparator
                .comparingInt((String s) -> switch (s.toLowerCase()) {
                    case "são paulo"      -> 0;
                    case "rio de janeiro" -> 1;
                    default -> 2;
                })
                .thenComparing(String::compareToIgnoreCase);

        return stateNames.stream()
                .sorted(customComparator)
                .toList();
    }

    @Override
    public List<String> fetchMunicipalities(String stateId) {
        String url = ibgeBaseUrl + "/estados/" + stateId + "/municipios";
        List<Map<String, Object>> municipalities = Arrays.asList(
                restTemplate.getForObject(url, Map[].class)
        );

        return municipalities.stream()
                .map(m -> (String) m.get("nome"))
                .collect(Collectors.toList());
    }
}
