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
    public ClientDomain findClientBySocialId(String cpf) {
        return clientRepository.findClientByCpf(cpf);
    }

    @Override
    public ClientDomain updateClientBySocialId(String cpf, AddressDomain newAddress) {
        ClientDomain clientDomain = clientRepository.findClientByCpf(cpf);
        if (clientDomain != null) {
            return clientRepository.updateClientByCpf(cpf, new ClientDomain(clientDomain.socialId(), clientDomain.name(), newAddress));
        }
        return null;
    }
}
