package br.com.pan.changeadress.application.ports.in;

import br.com.pan.changeadress.domain.AddressDomain;
import br.com.pan.changeadress.domain.ClientDomain;

public interface ClientServicePort {
    ClientDomain findClientByCpf(String cpf);
    ClientDomain updateClientByCpf(String cpf, AddressDomain address);
}
