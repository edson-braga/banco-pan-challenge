package br.com.pan.changeaddress.application.ports.out;

import br.com.pan.changeaddress.domain.ClientDomain;

public interface ClientRepositoryPort {
    ClientDomain findClientByCpf(String cpf);
    ClientDomain updateClientByCpf(String cpf, ClientDomain client);
}
