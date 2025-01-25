package br.com.pan.changeadress.adapters.out.persistence;

import br.com.pan.changeadress.domain.ClientDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientDomain, Integer> {}
