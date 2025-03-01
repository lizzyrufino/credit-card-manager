package desafio.altbank.spring.credit_card_manager.controllers.dto.response;

import desafio.altbank.spring.credit_card_manager.domain.entities.CreditCard;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CreditCardResponse(
        Long id,
        String name,
        String cardNumber,
        String cvv,
        LocalDateTime expirationDate,
        LocalDateTime createdAt,
        String type
) {
    public static CreditCardResponse fromDomain(CreditCard newCreditCard) {
        return CreditCardResponse.builder()
                .id(newCreditCard.getId())
                .name(newCreditCard.getName())
                .cardNumber(newCreditCard.getCardNumber())
                .cvv(newCreditCard.getCvv())
                .expirationDate(newCreditCard.getExpirationDate())
                .createdAt(newCreditCard.getCreatedAt())
                .type(newCreditCard.getType().getValue())
                .build();
    }
}
