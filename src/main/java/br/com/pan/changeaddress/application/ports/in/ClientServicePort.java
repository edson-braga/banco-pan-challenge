package br.com.pan.changeaddress.application.ports.in;

import br.com.pan.changeaddress.domain.AddressDomain;
import br.com.pan.changeaddress.domain.ClientDomain;

public interface ClientServicePort {
    ClientDomain findClientBySocialId(String cpf);
    ClientDomain updateClientBySocialId(String cpf, AddressDomain address);
}
