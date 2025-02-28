package desafio.altbank.spring.credit_card_manager.controllers;

import desafio.altbank.spring.credit_card_manager.controllers.dto.request.CreateAccountRequest;
import desafio.altbank.spring.credit_card_manager.controllers.dto.response.AccountResponse;
import desafio.altbank.spring.credit_card_manager.controllers.swagger.AccountControllerSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController implements AccountControllerSwagger {
    @Override
    public ResponseEntity<AccountResponse> post(CreateAccountRequest createAccountRequest) {
        return null;
    }
}
