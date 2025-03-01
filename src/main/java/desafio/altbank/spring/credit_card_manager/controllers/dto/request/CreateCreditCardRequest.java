package desafio.altbank.spring.credit_card_manager.controllers.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CreateCreditCardRequest(
        @NotEmpty
        String name,
        @NotEmpty
        String cardNumber,
        @NotEmpty
        String cvv,
        @NotEmpty
        LocalDateTime expirationDate,
        @NotEmpty
        String type
) {

}
