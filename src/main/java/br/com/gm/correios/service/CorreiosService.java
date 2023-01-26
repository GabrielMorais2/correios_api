package br.com.gm.correios.service;

import br.com.gm.correios.exception.NoContentException;
import br.com.gm.correios.model.Address;
import br.com.gm.correios.model.AddressStatus;
import br.com.gm.correios.model.Status;
import br.com.gm.correios.repository.AddressRepository;
import br.com.gm.correios.repository.AddressStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CorreiosService {

    @Autowired
    private AddressStatusRepository addressStatusRepository;

    @Autowired
    private AddressRepository addressRepository;


    public Status getStatus(){
        return this.addressStatusRepository.findById(Address.DEFAULT_ID)
                .orElse(AddressStatus.builder().status(Status.NEED_SETUP).build())
                .getStatus();
    }

    public Address getAddressByZipcode(String zipcode) throws NoContentException {
        return addressRepository.findById(zipcode)
                .orElseThrow(NoContentException::new);
    }

    public void setup(){

    }

}
