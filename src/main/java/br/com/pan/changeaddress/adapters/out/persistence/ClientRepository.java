package br.com.pan.changeaddress.adapters.out.persistence;

import br.com.pan.changeaddress.adapters.out.persistence.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, String> {}
