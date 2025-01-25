package br.com.pan.changeadress.adapters.out.persistence;

import br.com.pan.changeadress.domain.AddressDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressDomain, Integer> {}
