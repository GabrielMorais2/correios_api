package br.com.gm.correios.service;

import br.com.gm.correios.CorreiosApplication;
import br.com.gm.correios.exception.NoContentException;
import br.com.gm.correios.exception.NotReadyException;
import br.com.gm.correios.repository.SetupRepository;
import br.com.gm.correios.model.Address;
import br.com.gm.correios.model.AddressStatus;
import br.com.gm.correios.model.Status;
import br.com.gm.correios.repository.AddressRepository;
import br.com.gm.correios.repository.AddressStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class CorreiosService {
    private static Logger logger = LoggerFactory.getLogger((CorreiosService.class));

    @Autowired
    private AddressStatusRepository addressStatusRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private SetupRepository setupRepository;


    public Status getStatus(){
        return this.addressStatusRepository.findById(Address.DEFAULT_ID)
                .orElse(AddressStatus.builder().status(Status.NEED_SETUP).build())
                .getStatus();
    }

    public Address getAddressByZipcode(String zipcode) throws NoContentException, NotReadyException {
        if(!this.getStatus().equals(Status.READY)){
            throw new NotReadyException();
        }

        return addressRepository.findById(zipcode)
                .orElseThrow(NoContentException::new);
    }

    private void saveStatus(Status status){
        this.addressStatusRepository.save(AddressStatus.builder()
                .id(AddressStatus.DEFAULT_ID)
                .status(status)
                .build());
    }

    @EventListener(ApplicationStartedEvent.class)
    protected void setupOnStartup(){
        try {
            this.setup();
        } catch (Exception ex) {
            CorreiosApplication.close(999);
        }
    }

    public void setup() throws Exception {
        logger.info("------");
        logger.info("------");
        logger.info("------ SETUP RUNNING");
        logger.info("------");
        logger.info("------");

        if(this.getStatus().equals(Status.NEED_SETUP)){

           try{
               this.saveStatus(Status.SETUP_RUNNING);
               this.addressRepository.saveAll(this.setupRepository.getFromOrigin());
           } catch (Exception ex){
               this.saveStatus(Status.NEED_SETUP);
               throw ex;
           }

            this.saveStatus(Status.READY);
        }
        logger.info("------");
        logger.info("------");
        logger.info("------ SERVICE READY");
        logger.info("------");
        logger.info("------");
    }

}
