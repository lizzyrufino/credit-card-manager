package desafio.altbank.spring.credit_card_manager.domain.services;

import desafio.altbank.spring.credit_card_manager.configuration.MessageConfig;
import desafio.altbank.spring.credit_card_manager.controllers.dto.request.CreateAccountRequest;
import desafio.altbank.spring.credit_card_manager.controllers.dto.response.AccountResponse;
import desafio.altbank.spring.credit_card_manager.domain.entities.Account;
import desafio.altbank.spring.credit_card_manager.exception.InternalServerErrorException;
import desafio.altbank.spring.credit_card_manager.exception.RestException;
import desafio.altbank.spring.credit_card_manager.infraestructure.database.AccountRepository;
import desafio.altbank.spring.credit_card_manager.infraestructure.database.CreditCardRepository;
import desafio.altbank.spring.credit_card_manager.mock.AccountMock;
import desafio.altbank.spring.credit_card_manager.services.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    AccountRepository accountRepositoryMock;

    @Mock
    CreditCardRepository creditCardRepositoryMock;

    @Mock
    MessageConfig messageConfigMock;

    @InjectMocks
    AccountService accountService;

    private CreateAccountRequest createAccountRequest;
    private AccountResponse accountResponse;
    private RestException restException;

    @Test
    @DisplayName("Criar uma nova conta com sucesso")
    void shouldCreateAccountService() {
        givenCreateAccountRequest();
        givenAccountRepositoryCallExistsByCpfReturnsFalse();
        givenAccountRepositoryCallSaveSuccessfully();

        whenAccountServiceCallCreateAccountSuccessfully();

        thenAccountRepositoryCallExistsByCpfAtLeastOnce();
        thenAccountRepositoryCallSaveAtLeastOnce();
        thenAccountServiceCreateAccountReturnsAccountResponseValid();
    }

    @Test
    @DisplayName("Deve lançar excessão quando a conta ja existir")
    void shouldCreateAccountServiceAlreadyExists() {
        givenCreateAccountRequest();
        givenAccountRepositoryCallExistsByCpfReturnsTrue();

        whenAccountServiceCallCreateAccountThrowsException();

        thenAccountRepositoryCallExistsByCpfAtLeastOnce();
        thenAccountRepositoryCallSaveNever();
        thenExceptionIsInternalServerError();
    }

    // GIVEN
    private void givenCreateAccountRequest() {
        this.createAccountRequest = AccountMock.createAccountRequestMock();
    }

    private void givenAccountRepositoryCallExistsByCpfReturnsFalse() {
        when(accountRepositoryMock.existsByCpf(anyString())).thenReturn(false);
    }

    private void givenAccountRepositoryCallExistsByCpfReturnsTrue() {
        when(accountRepositoryMock.existsByCpf(anyString())).thenReturn(true);
    }

    private void givenAccountRepositoryCallSaveSuccessfully() {
        when(accountRepositoryMock.save(any())).thenReturn(AccountMock.accountMock());
    }

    // WHEN
    private void whenAccountServiceCallCreateAccountSuccessfully() {
        this.accountResponse = accountService.createAccount(this.createAccountRequest);
    }

    private void whenAccountServiceCallCreateAccountThrowsException() {
        this.restException = assertThrows(InternalServerErrorException.class, () -> accountService.createAccount(this.createAccountRequest));
    }

    // THEN
    private void thenAccountRepositoryCallExistsByCpfAtLeastOnce() {
        verify(accountRepositoryMock, atLeastOnce()).existsByCpf(anyString());
    }

    private void thenAccountRepositoryCallSaveAtLeastOnce() {
        verify(accountRepositoryMock, atLeastOnce()).save(any(Account.class));
    }

    private void thenAccountRepositoryCallSaveNever() {
        verify(accountRepositoryMock, never()).save(any(Account.class));
    }

    private void thenAccountServiceCreateAccountReturnsAccountResponseValid() {
        assertNotNull(this.accountResponse);
    }

    private void thenExceptionIsInternalServerError() {
        assertNotNull(this.restException);
        assertEquals(this.restException.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}