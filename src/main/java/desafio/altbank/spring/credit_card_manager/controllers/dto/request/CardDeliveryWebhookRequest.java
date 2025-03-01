package desafio.altbank.spring.credit_card_manager.controllers.dto.request;


import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CardDeliveryWebhookRequest(
         String trackingId,
         String deliveryStatus,
         LocalDateTime deliveryDate,
         String deliveryReturnReason,
         String deliveryAddress
) {
}
