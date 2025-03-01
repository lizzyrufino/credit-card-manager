package desafio.altbank.spring.credit_card_manager.mock;

import desafio.altbank.spring.credit_card_manager.controllers.dto.request.CreateAccountRequest;
import desafio.altbank.spring.credit_card_manager.domain.entities.Account;

public class AccountMock {
    public static CreateAccountRequest createAccountRequestMock() {
        return new  CreateAccountRequest(
                "name",
                "email@domain.com",
                "99999999999",
                "00911111111",
                "Rua dos Suruís, Teixeirão, Cacoal-RO, 76965-620"
        );
    }

    public static Account accountMock() {
        return Account.builder()
                .id(1231231L)
                .name("name")
                .phone("00911111111")
                .email("email@domain.com")
                .status(true)
                .address("Rua dos Suruís, Teixeirão, Cacoal-RO, 76965-620")
                .cpf("99999999999")
                .build();
    }
}
