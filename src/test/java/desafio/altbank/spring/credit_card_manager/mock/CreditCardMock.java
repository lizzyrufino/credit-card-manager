package desafio.altbank.spring.credit_card_manager.mock;

import desafio.altbank.spring.credit_card_manager.controllers.dto.request.CreateCreditCardRequest;
import desafio.altbank.spring.credit_card_manager.controllers.dto.request.CvvUpdateWebhookRequest;
import desafio.altbank.spring.credit_card_manager.domain.entities.CreditCard;
import desafio.altbank.spring.credit_card_manager.domain.enums.CreditCardType;

import java.time.LocalDateTime;

public class CreditCardMock {
    public static CvvUpdateWebhookRequest cvvUpdateWebhookRequestMock() {
        return new  CvvUpdateWebhookRequest(
                "324",
                123L,
                "9999",
                 LocalDateTime.now().plusYears(3)
        );
    }
    public static CreditCard virtualCreditCardMock() {
        return CreditCard.builder()
                .id(123L)
                .name("Teste Card")
                .cardNumber("1234123412341234")
                .cvv("123")
                .expirationDate(LocalDateTime.now().plusYears(3))
                .type(CreditCardType.VIRTUAL_CARD)
                .trackingId("TRACK123")
                .deliveryStatus("DELIVERED")
                .lastCvvUpdate(LocalDateTime.now())
                .build();
    }
    public static CreditCard physicalCreditCardMock() {
        return CreditCard.builder()
                .id(123L)
                .name("Teste Card")
                .cardNumber("1234123412341234")
                .cvv("134")
                .expirationDate(LocalDateTime.now().plusYears(3))
                .type(CreditCardType.CARD)
                .trackingId("TRACK1254")
                .deliveryStatus("DELIVERED")
                .lastCvvUpdate(LocalDateTime.now())
                .build();
    }
}
