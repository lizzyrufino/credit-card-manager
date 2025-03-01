package desafio.altbank.spring.credit_card_manager.exception;

import org.springframework.http.HttpStatus;

public abstract class RestException extends RuntimeException {
    private final String code;
    public abstract HttpStatus getStatus();
    protected RestException(String code, String message) {
        super(message);
        this.code = code;
    }

}
