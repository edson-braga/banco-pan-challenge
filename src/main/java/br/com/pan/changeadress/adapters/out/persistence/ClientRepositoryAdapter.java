package br.com.pan.changeadress.adapters.out.persistence;

import br.com.pan.changeadress.adapters.exceptions.ClientNotFoundException;
import br.com.pan.changeadress.adapters.out.converters.ClientConverter;
import br.com.pan.changeadress.adapters.out.external.AddressExternalAdapter;
import br.com.pan.changeadress.adapters.out.persistence.entities.AddressEntity;
import br.com.pan.changeadress.adapters.out.persistence.entities.ClientEntity;
import br.com.pan.changeadress.application.ports.out.ClientRepositoryPort;
import br.com.pan.changeadress.domain.AddressDomain;
import br.com.pan.changeadress.domain.ClientDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ClientRepositoryAdapter implements ClientRepositoryPort {

    private static final Logger logger = LoggerFactory.getLogger(ClientRepositoryAdapter.class);

    private final ClientRepository jpaClientRepository;

    public ClientRepositoryAdapter(ClientRepository clientRepository) {
        this.jpaClientRepository = clientRepository;
    }

    @Override
    public ClientDomain findClientByCpf(String cpf) {
        logger.info("Find client by socialId = {}", cpf);

        var clientEntity = jpaClientRepository.findById(cpf)
                .orElseThrow(() -> new ClientNotFoundException(
                        "Client not found with CPF: " + cpf,
                        "CLIENT_NOT_FOUND"
                ));
        return ClientConverter.toDomain(clientEntity);
    }

    @Override
    public ClientDomain updateClientByCpf(String cpf, ClientDomain clientDomain) {
        jpaClientRepository.findById(cpf)
                .orElseThrow(() -> new ClientNotFoundException(
                        "Cannot update non-existing client with CPF: " + cpf,
                        "CLIENT_NOT_FOUND"
                ));

        var entityToSave = ClientConverter.toEntity(clientDomain);
        logger.info("Client that will be updated = {}", entityToSave.toString());

        var savedEntity = jpaClientRepository.save(entityToSave);
        return ClientConverter.toDomain(savedEntity);
    }
}
