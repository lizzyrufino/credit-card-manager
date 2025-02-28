package desafio.altbank.spring.credit_card_manager.service;

import desafio.altbank.spring.credit_card_manager.controllers.dto.request.CreateAccountRequest;
import desafio.altbank.spring.credit_card_manager.controllers.dto.response.AccountResponse;
import desafio.altbank.spring.credit_card_manager.domain.ports.AccountPort;

public class AccountService implements AccountPort {
    @Override
    public AccountResponse createAccount(CreateAccountRequest request) {
        return null;
    }
}
