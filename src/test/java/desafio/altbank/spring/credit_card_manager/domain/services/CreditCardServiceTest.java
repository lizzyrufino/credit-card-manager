package desafio.altbank.spring.credit_card_manager.domain.services;

import desafio.altbank.spring.credit_card_manager.controllers.dto.request.CvvUpdateWebhookRequest;
import desafio.altbank.spring.credit_card_manager.domain.entities.CreditCard;
import desafio.altbank.spring.credit_card_manager.exception.RestException;
import desafio.altbank.spring.credit_card_manager.infraestructure.database.CreditCardRepository;
import desafio.altbank.spring.credit_card_manager.mock.CreditCardMock;
import desafio.altbank.spring.credit_card_manager.services.CreditCardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CreditCardServiceTest {
    @Mock
    CreditCardRepository creditCardRepositoryMock;

    @InjectMocks
    CreditCardService creditCardService;

    CreditCard creditCard;
    private RestException restException;
    private CvvUpdateWebhookRequest cvvUpdateWebhookRequest;
    @Test
    @DisplayName("Deve atualizar o CVV com sucesso")
    void shouldUpdateCvvSuccessfully() {
        givenCreditCardExists();
        givenCvvUpdateRequest();
        givenCreditCardRepositoryFindByIdSuccessfull();

        whenProcessCvvUpdateIsCalled();

        thenCreditCardIsUpdated();
    }
    @Test
    @DisplayName("Deve lançar exceção se o cartão não for encontrado")
    void shouldThrowExceptionWhenCardNotFound() {
        givenCreditCardExists();
        givenCvvUpdateRequest();
        givenCreditCardRepositoryFindByIdNotFound();

        whenProcessCvvUpdateThrowsException();

        thenThrowRestException();
    }

    @Test
    @DisplayName("Deve lançar exceção se o cartão não for virtual")
    void shouldThrowExceptionWhenCardIsNotVirtual() {
        givenCreditCardExists();
        givenCvvUpdateRequest();
        givenCreditCardRepositoryFindByIdReturnPhysicalCard();

        whenProcessCvvUpdateThrowsException();

        thenThrowRestException();
    }

    // GIVEN
    private void givenCreditCardExists() {
        creditCard = CreditCardMock.virtualCreditCardMock();

    }
    private void givenCreditCardRepositoryFindByIdSuccessfull(){
        when(creditCardRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(creditCard));
    }
    private void givenCreditCardRepositoryFindByIdNotFound(){
        when(creditCardRepositoryMock.findById(any(Long.class))).thenReturn(Optional.empty());
    }

    private void givenCreditCardRepositoryFindByIdReturnPhysicalCard() {
        CreditCard physicalCard = CreditCardMock.physicalCreditCardMock();
        when(creditCardRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(physicalCard));
    }

    private void givenCvvUpdateRequest() {
        cvvUpdateWebhookRequest = CreditCardMock.cvvUpdateWebhookRequestMock();
    }


    // WHEN
    private void whenProcessCvvUpdateIsCalled() {
        creditCardService.processCvvUpdate(cvvUpdateWebhookRequest, creditCard.getId());
    }

    private void whenProcessCvvUpdateThrowsException() {
        restException = assertThrows(RestException.class, () -> creditCardService.processCvvUpdate(cvvUpdateWebhookRequest, creditCard.getId()));
    }

    // THEN
    private void thenCreditCardIsUpdated() {
        verify(creditCardRepositoryMock, atLeastOnce()).save(any(CreditCard.class));
    }

    private void thenThrowRestException() {
        assertNotNull(restException);
    }
}