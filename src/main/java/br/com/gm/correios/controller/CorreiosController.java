package br.com.gm.correios.controller;

import br.com.gm.correios.model.Address;
import jakarta.websocket.server.PathParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorreiosController {

    @GetMapping("/status")
    public String getStatus(){
        return "UP";
    }

    @GetMapping("/zipcode/{zipcode}")
    public Address getAddressByZipcode(@PathParam("zipcode") String zipcode){
        return Address.builder().zipcode(zipcode).build();
    }

}
