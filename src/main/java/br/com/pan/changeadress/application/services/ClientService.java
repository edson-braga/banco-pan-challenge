package br.com.pan.changeadress.application.services;

import br.com.pan.changeadress.application.ports.in.ClientServicePort;
import br.com.pan.changeadress.application.ports.out.ClientRepositoryPort;
import br.com.pan.changeadress.domain.AddressDomain;
import br.com.pan.changeadress.domain.ClientDomain;
import org.springframework.stereotype.Service;

@Service
public class ClientService implements ClientServicePort {

    private final ClientRepositoryPort clientRepository;

    public ClientService(ClientRepositoryPort clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDomain findClientByCpf(String cpf) {
        return clientRepository.findClientByCpf(cpf);
    }

    @Override
    public ClientDomain updateClientByCpf(String cpf, AddressDomain newData) {
        ClientDomain clientDomain = clientRepository.findClientByCpf(cpf);
        if (clientDomain != null) {
            clientDomain.setAddress(newData);
            return clientRepository.updateClientByCpf(cpf, clientDomain);
        }
        return null;
    }
}
