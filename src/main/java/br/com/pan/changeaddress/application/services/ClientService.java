package br.com.pan.changeaddress.application.services;

import br.com.pan.changeaddress.application.ports.in.ClientServicePort;
import br.com.pan.changeaddress.application.ports.out.ClientRepositoryPort;
import br.com.pan.changeaddress.domain.AddressDomain;
import br.com.pan.changeaddress.domain.ClientDomain;
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
