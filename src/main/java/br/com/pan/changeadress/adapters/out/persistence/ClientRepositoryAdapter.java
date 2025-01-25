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
            domain.getAddress().setCep(entity.getAddress().getCep());
            domain.getAddress().setLogradouro(entity.getAddress().getLogradouro());
            domain.getAddress().setNumero(entity.getAddress().getNumero());
            domain.getAddress().setComplemento(entity.getAddress().getComplemento());
            domain.getAddress().setBairro(entity.getAddress().getBairro());
            domain.getAddress().setCidade(entity.getAddress().getCidade());
            domain.getAddress().setEstado(entity.getAddress().getEstado());
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
            addressEntity.setCep(domain.getAddress().getCep());
            addressEntity.setLogradouro(domain.getAddress().getLogradouro());
            addressEntity.setNumero(domain.getAddress().getNumero());
            addressEntity.setComplemento(domain.getAddress().getComplemento());
            addressEntity.setBairro(domain.getAddress().getBairro());
            addressEntity.setCidade(domain.getAddress().getCidade());
            addressEntity.setEstado(domain.getAddress().getEstado());
            addressEntity.setCliente(entity);
            entity.setAddress(addressEntity);
        }
        return entity;
    }
}
