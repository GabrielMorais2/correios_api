package br.com.gm.correios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE, reason = "Serviço está em instalação. Favor aguardar de 3 a 5 minutos") // error 503
public class NotReadyException extends Exception{

}
