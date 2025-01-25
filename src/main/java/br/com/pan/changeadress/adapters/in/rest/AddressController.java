package br.com.pan.changeadress.adapters.in.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/enderecos")
public class AddressController {

    private final RestTemplate restTemplate;

    public AddressController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{cep}")
    public String consultarEnderecoPorCep(@PathVariable String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json";
        return restTemplate.getForObject(url, String.class);
    }

    @GetMapping("/estados")
    public String consultarEstados() {
        String url = "https://servicodados.ibge.gov.br/api/v1/localidades/estados";
        return restTemplate.getForObject(url, String.class);
    }

    @GetMapping("/estados/{id}/municipios")
    public String consultarMunicipiosPorEstado(@PathVariable String id) {
        String url = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/" + id + "/municipios";
        return restTemplate.getForObject(url, String.class);
    }
}
