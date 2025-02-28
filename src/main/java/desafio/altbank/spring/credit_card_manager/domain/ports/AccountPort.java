package desafio.altbank.spring.credit_card_manager.domain.ports;

import desafio.altbank.spring.credit_card_manager.controllers.dto.request.CreateAccountRequest;
import desafio.altbank.spring.credit_card_manager.controllers.dto.response.AccountResponse;

public interface AccountPort {

    AccountResponse createAccount(CreateAccountRequest request);
}
