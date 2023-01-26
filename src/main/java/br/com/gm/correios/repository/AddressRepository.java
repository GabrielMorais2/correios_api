package br.com.gm.correios.repository;

import br.com.gm.correios.model.Address;
import br.com.gm.correios.model.AddressStatus;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, String> {
}
