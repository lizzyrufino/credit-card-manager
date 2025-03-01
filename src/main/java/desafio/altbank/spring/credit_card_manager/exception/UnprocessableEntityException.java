package desafio.altbank.spring.credit_card_manager.exception;

import org.springframework.http.HttpStatus;

public class UnprocessableEntityException extends RestException {

    public UnprocessableEntityException(String code, String message) {
        super(code, message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }

}
