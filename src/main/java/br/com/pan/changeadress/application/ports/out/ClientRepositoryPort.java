package br.com.pan.changeadress.application.ports.out;

import br.com.pan.changeadress.domain.ClientDomain;

public interface ClientRepositoryPort {
    ClientDomain findClientByCpf(String cpf);
    ClientDomain updateClientByCpf(String cpf, ClientDomain client);
}
