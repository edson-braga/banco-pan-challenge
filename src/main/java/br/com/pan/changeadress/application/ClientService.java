package br.com.pan.changeadress.application;

import br.com.pan.changeadress.domain.AddressDomain;
import br.com.pan.changeadress.domain.ClientDomain;

public interface ClientService {
    ClientDomain findClientBySocialId(String socialId);
    void changeAdress(String socialId, AddressDomain adress);
}
