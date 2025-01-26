package br.com.pan.changeadress.application.ports.in;

import br.com.pan.changeadress.domain.AddressDomain;
import br.com.pan.changeadress.domain.ClientDomain;

public interface ClientServicePort {
    ClientDomain findClientBySocialId(String cpf);
    ClientDomain updateClientBySocialId(String cpf, AddressDomain address);
}
