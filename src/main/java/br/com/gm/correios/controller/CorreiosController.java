package br.com.gm.correios.controller;

import br.com.gm.correios.exception.NoContentException;
import br.com.gm.correios.model.Address;
import br.com.gm.correios.service.CorreiosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorreiosController {

    @Autowired
    private CorreiosService correiosService;

    @GetMapping("/status")
    public String getStatus(){
        return "Service Status: " + this.correiosService.getStatus();
    }

    @GetMapping("/zipcode/{zipcode}")
    public Address getAddressByZipcode(@PathVariable String zipcode) throws NoContentException {
        return this.correiosService.getAddressByZipcode(zipcode);
    }

}
