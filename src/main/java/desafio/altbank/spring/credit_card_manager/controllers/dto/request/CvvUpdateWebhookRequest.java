package desafio.altbank.spring.credit_card_manager.controllers.dto.request;

import lombok.Builder;

import java.time.LocalDateTime;

//dto pra receber o webhook
@Builder
public record CvvUpdateWebhookRequest(
         String accountId,
         Long cardId,
         String nextCvv,
         LocalDateTime expirationDate
) {
}
