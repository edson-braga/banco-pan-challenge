package br.com.pan.changeadress.adapters.in.rest;

import br.com.pan.changeadress.application.ports.in.ClientServicePort;
import br.com.pan.changeadress.domain.AddressDomain;
import br.com.pan.changeadress.domain.ClientDomain;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClientController {

    private final ClientServicePort clientService;

    public ClientController(ClientServicePort clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{cpf}")
    public ClientDomain consultarCliente(@PathVariable String cpf) {
        return clientService.findClientByCpf(cpf);
    }

    @PutMapping("/{cpf}/endereco")
    public ClientDomain alterarEndereco(@PathVariable String cpf, @RequestBody AddressDomain newData) {
        return clientService.updateClientByCpf(cpf, newData);
    }
}
