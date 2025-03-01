package desafio.altbank.spring.credit_card_manager.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageErrorCodeConstants {

    //HTTP 404
    public static final String CARD_NOT_FOUND = "404.000";
    public static final String ACCOUNT_NOT_FOUND = "404.001";

    //HTTP 422
    public static final String CPF_ALREADY_EXISTS = "422.001";
    public static final String CARD_ALREADY_ACTIVE = "422.002";

    public static final String ACCOUNT_ALREADY_INACTIVE = "422.003";


    //HTTP 500
    public static final String INTERNAL_SERVER_ERROR = "500.000";

    public static final String UNEXPECTED_SERVER_ERROR = "500.001";

}
