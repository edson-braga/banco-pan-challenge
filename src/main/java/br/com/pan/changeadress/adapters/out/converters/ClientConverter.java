package br.com.pan.changeadress.adapters.out.converters;

import br.com.pan.changeadress.adapters.out.persistence.entities.AddressEntity;
import br.com.pan.changeadress.adapters.out.persistence.entities.ClientEntity;
import br.com.pan.changeadress.domain.AddressDomain;
import br.com.pan.changeadress.domain.ClientDomain;

public final class ClientConverter {

    private ClientConverter() {}

    public static ClientDomain toDomain(ClientEntity entity) {
        if (entity == null) return null;

        var addressEntity = entity.getAddress();
        AddressDomain addressDomain = null;
        if (addressEntity != null) {
            addressDomain = new AddressDomain(
                    addressEntity.getCep(),
                    addressEntity.getLogradouro(),
                    addressEntity.getNumero(),
                    addressEntity.getComplemento(),
                    addressEntity.getBairro(),
                    addressEntity.getCidade(),
                    addressEntity.getEstado()
            );
        }

        return new ClientDomain(entity.getCpf(), entity.getNome(), addressDomain);
    }

    public static ClientEntity toEntity(ClientDomain domain) {
        if (domain == null) return null;

        var clientEntity = new ClientEntity();
        clientEntity.setCpf(domain.socialId());
        clientEntity.setNome(domain.name());

        if (domain.address() != null) {
            var address = new AddressEntity();
            address.setCep(domain.address().zipCode());
            address.setLogradouro(domain.address().street());
            address.setNumero(domain.address().number());
            address.setComplemento(domain.address().complement());
            address.setBairro(domain.address().neighborhood());
            address.setCidade(domain.address().city());
            address.setEstado(domain.address().state());
            address.setCliente(clientEntity);

            clientEntity.setAddress(address);
        }

        return clientEntity;
    }
}
