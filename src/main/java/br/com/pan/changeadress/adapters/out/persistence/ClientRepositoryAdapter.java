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

        return new ClientDomain(entity.getCpf(), entity.getNome(), new AddressDomain(
                entity.getAddress().getCep(),
                entity.getAddress().getLogradouro(),
                entity.getAddress().getNumero(),
                entity.getAddress().getComplemento(),
                entity.getAddress().getBairro(),
                entity.getAddress().getCidade(),
                entity.getAddress().getEstado()));
    }

    private ClientEntity toEntity(ClientDomain domain) {
        if (domain == null) return null;

        ClientEntity entity = new ClientEntity();
        entity.setCpf(domain.cpf());
        entity.setNome(domain.nome());

        if (domain.address() != null) {
            var addressEntity = new AddressEntity();
            addressEntity.setCep(domain.address().zipCode());
            addressEntity.setLogradouro(domain.address().street());
            addressEntity.setNumero(domain.address().number());
            addressEntity.setComplemento(domain.address().complement());
            addressEntity.setBairro(domain.address().neighborhood());
            addressEntity.setCidade(domain.address().city());
            addressEntity.setEstado(domain.address().state());
            addressEntity.setCliente(entity);
            entity.setAddress(addressEntity);
        }
        return entity;
    }
}
