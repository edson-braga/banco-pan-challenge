package br.com.pan.changeadress.application;

import br.com.pan.changeadress.domain.AddressDomain;
import br.com.pan.changeadress.domain.ClientDomain;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl extends ClientService {


    @Override
    public ClientDomain findClientBySocialId(String socialId) {
        return null;
    }

    @Override
    public void changeAdress(String socialId, AddressDomain adress) {

    }
}
