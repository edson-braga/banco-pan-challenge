package br.com.pan.changeadress.adapters.out.persistence;

import br.com.pan.changeadress.adapters.out.persistence.entities.AddressEntity;
import br.com.pan.changeadress.adapters.out.persistence.entities.ClientEntity;
import br.com.pan.changeadress.application.ports.out.ClientRepositoryPort;
import br.com.pan.changeadress.domain.AddressDomain;
import br.com.pan.changeadress.domain.ClientDomain;
import org.springframework.stereotype.Component;

@Component
public class ClientRepositoryAdapter implements ClientRepositoryPort {

    private final ClientRepository jpaClientRepository;

    public ClientRepositoryAdapter(ClientRepository clientRepository) {
        this.jpaClientRepository = clientRepository;
    }

    @Override
    public ClientDomain findClientByCpf(String cpf) {
        return jpaClientRepository.findById(cpf)
                .map(this::toDomain)
                .orElse(null);
    }

    @Override
    public ClientDomain updateClientByCpf(String cpf, ClientDomain clientDomain) {
        ClientEntity clientEntity = toEntity(clientDomain);
        ClientEntity savedEntity = jpaClientRepository.save(clientEntity);
        return toDomain(savedEntity);
    }

    private ClientDomain toDomain(ClientEntity entity) {
        if (entity == null) return null;

        ClientDomain domain = new ClientDomain();
        domain.setCpf(entity.getCpf());
        domain.setNome(entity.getNome());
        if (entity.getAddress() != null) {
            domain.setAddress(new AddressDomain());
            domain.getAddress().setZipCode(entity.getAddress().getCep());
            domain.getAddress().setStreet(entity.getAddress().getLogradouro());
            domain.getAddress().setNumber(entity.getAddress().getNumero());
            domain.getAddress().setComplement(entity.getAddress().getComplemento());
            domain.getAddress().setNeighborhood(entity.getAddress().getBairro());
            domain.getAddress().setCity(entity.getAddress().getCidade());
            domain.getAddress().setState(entity.getAddress().getEstado());
        }
        return domain;
    }

    private ClientEntity toEntity(ClientDomain domain) {
        if (domain == null) return null;

        ClientEntity entity = new ClientEntity();
        entity.setCpf(domain.getCpf());
        entity.setNome(domain.getNome());

        if (domain.getAddress() != null) {
            var addressEntity = new AddressEntity();
            addressEntity.setCep(domain.getAddress().getZipCode());
            addressEntity.setLogradouro(domain.getAddress().getStreet());
            addressEntity.setNumero(domain.getAddress().getNumber());
            addressEntity.setComplemento(domain.getAddress().getComplement());
            addressEntity.setBairro(domain.getAddress().getNeighborhood());
            addressEntity.setCidade(domain.getAddress().getCity());
            addressEntity.setEstado(domain.getAddress().getState());
            addressEntity.setCliente(entity);
            entity.setAddress(addressEntity);
        }
        return entity;
    }
}
