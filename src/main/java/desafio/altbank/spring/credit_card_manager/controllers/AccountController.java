package desafio.altbank.spring.credit_card_manager.controllers;

import desafio.altbank.spring.credit_card_manager.controllers.dto.request.CreateAccountRequest;
import desafio.altbank.spring.credit_card_manager.controllers.dto.response.AccountResponse;
import desafio.altbank.spring.credit_card_manager.controllers.swagger.AccountControllerSwagger;
import desafio.altbank.spring.credit_card_manager.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController implements AccountControllerSwagger {
    private final AccountService accountService;

    @PostMapping("/accounts")
    public ResponseEntity<AccountResponse> createAccount(@RequestBody CreateAccountRequest request) {
        accountService.createAccount(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountResponse>> listAccount(@RequestParam String cpf) {
        accountService.listAccount(cpf);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/{accountId}/inactivate")
    public ResponseEntity<AccountResponse> inactivateAccount(@PathVariable Long accountId) {
        accountService.inactivateAccount(accountId);
        return ResponseEntity.noContent().build();
    }
}

