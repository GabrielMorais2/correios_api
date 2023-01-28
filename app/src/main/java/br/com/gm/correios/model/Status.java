package br.com.gm.correios.model;

public enum Status {

    NEED_SETUP,       //PRECISA BAIXAR O CSV DOS CORREIOS
    SETUP_RUNNING,    //ESTÁ BAIXANDO/SALVANDO NO BANCO
    READY;            // SERVIÇO ESTA PRONTO PARA SER CONSUMIDO

}
