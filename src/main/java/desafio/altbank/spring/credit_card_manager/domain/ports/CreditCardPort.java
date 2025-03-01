package desafio.altbank.spring.credit_card_manager.domain.ports;

import desafio.altbank.spring.credit_card_manager.controllers.dto.request.CardDeliveryWebhookRequest;
import desafio.altbank.spring.credit_card_manager.controllers.dto.request.CvvUpdateWebhookRequest;
import desafio.altbank.spring.credit_card_manager.controllers.dto.response.CreditCardResponse;
import desafio.altbank.spring.credit_card_manager.domain.entities.CreditCard;

import java.util.List;

public interface CreditCardPort {
    CreditCardResponse createCard(Long accountId);
    List<CreditCard> listCards(Long accountId);
    void reissueCard(Long creditCardId, String reason);
    void activateCard(Long creditCardId);
    void blockCard(Long creditCardId);
    void processCvvUpdate(CvvUpdateWebhookRequest request, Long creditCardId);
    void processDeliveryUpdate(CardDeliveryWebhookRequest request);
}
