package br.com.pan.changeaddress.adapters.in.rest;

import br.com.pan.changeaddress.application.ports.in.ClientServicePort;
import br.com.pan.changeaddress.domain.AddressDomain;
import br.com.pan.changeaddress.domain.ClientDomain;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClientController {

    private final ClientServicePort clientService;

    public ClientController(ClientServicePort clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{socialId}")
    public ClientDomain findClientBySocialId(@PathVariable String socialId) {
        return clientService.findClientBySocialId(socialId);
    }

    @PutMapping("/{socialId}/endereco")
    public ClientDomain changeAddress(@PathVariable String socialId, @RequestBody AddressDomain newData) {
        return clientService.updateClientBySocialId(socialId, newData);
    }
}
