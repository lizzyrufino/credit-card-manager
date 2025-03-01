package desafio.altbank.spring.credit_card_manager.services;
import desafio.altbank.spring.credit_card_manager.configuration.MessageConfig;
import desafio.altbank.spring.credit_card_manager.controllers.dto.request.CardDeliveryWebhookRequest;
import desafio.altbank.spring.credit_card_manager.controllers.dto.request.CvvUpdateWebhookRequest;
import desafio.altbank.spring.credit_card_manager.controllers.dto.response.CreditCardResponse;
import desafio.altbank.spring.credit_card_manager.domain.entities.Account;
import desafio.altbank.spring.credit_card_manager.domain.entities.CreditCard;
import desafio.altbank.spring.credit_card_manager.domain.enums.CreditCardStatus;
import desafio.altbank.spring.credit_card_manager.domain.enums.CreditCardType;
import desafio.altbank.spring.credit_card_manager.domain.ports.CreditCardPort;
import desafio.altbank.spring.credit_card_manager.exception.InternalServerErrorException;
import desafio.altbank.spring.credit_card_manager.exception.NotFoundException;
import desafio.altbank.spring.credit_card_manager.exception.UnprocessableEntityException;
import desafio.altbank.spring.credit_card_manager.infraestructure.database.AccountRepository;
import desafio.altbank.spring.credit_card_manager.infraestructure.database.CreditCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreditCardService implements CreditCardPort {
    private final AccountRepository accountRepository;
    private final CreditCardRepository creditCardRepository;
    private final MessageConfig messageConfig;

    @Override
    @Transactional
    public CreditCardResponse createCard(Long accountId) {
        try {
            var account = accountRepository.findById(accountId)
                    .orElseThrow(() -> new NotFoundException("ACCOUNT_NOT_FOUND", "Conta não encontrada"));

            var newCard =  creditCardRepository.save(CreditCard.builder()
                    .account(account)
                    .cardNumber(generateNewCardNumber())
                    .name(account.getName())
                    .expirationDate(LocalDateTime.now().plusYears(4))
                    .status(CreditCardStatus.INACTIVE)
                    .type(CreditCardType.CARD)
                    .build());

            return CreditCardResponse.fromDomain(newCard);

        } catch (Exception ex) {
            throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", ex.getMessage());
        }
    }

    @Override
    @Transactional
    public void activateCard(Long creditCardId) {
        try {
            var creditCard = creditCardRepository.findById(creditCardId)
                    .orElseThrow(() -> new NotFoundException("CARD_NOT_FOUND", "Cartão não encontrado"));

            if (creditCard.getStatus() == CreditCardStatus.ACTIVE) {
                throw new UnprocessableEntityException("CARD_ALREADY_ACTIVE", "Cartão já está ativo");
            }

            if (creditCard.getType() == CreditCardType.VIRTUAL_CARD &&
                    !creditCardRepository.existsByAccountAndStatus(creditCard.getAccount(), CreditCardStatus.ACTIVE)) {
                throw new UnprocessableEntityException("FISICO_NOT_ACTIVE", "Cartão virtual só pode ser ativado após o físico ser entregue.");
            }

            creditCard.setStatus(CreditCardStatus.ACTIVE);
            creditCardRepository.save(creditCard);
        } catch (Exception ex) {
            throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", ex.getMessage());
        }
    }
    @Override
    @Transactional
    public void reissueCard(Long creditCardId, String reason) {
        try {
            var currentCard = creditCardRepository.findById(creditCardId)
                    .orElseThrow(() -> new NotFoundException("CARD_NOT_FOUND", "Cartão não encontrado"));

            currentCard.setStatus(CreditCardStatus.INACTIVE);
            creditCardRepository.save(currentCard);

            var newCard = CreditCard.builder()
                    .account(currentCard.getAccount())
                    .cardNumber(generateNewCardNumber())
                    .name(currentCard.getName())
                    .expirationDate(LocalDateTime.now().plusYears(4))
                    .status(CreditCardStatus.INACTIVE)
                    .type(currentCard.getType())
                    .reissueReason(reason)
                    .build();

            creditCardRepository.save(newCard);
        } catch (Exception ex) {
            throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", ex.getMessage());
        }
    }
    @Override
    @Transactional(readOnly = true)
    public List<CreditCard> listCards(Long accountId) {
        try {
            var account = accountRepository.findById(accountId)
                    .orElseThrow(() -> new NotFoundException("ACCOUNT_NOT_FOUND", "Conta não encontrada"));

            return creditCardRepository.findByAccount(account);

        } catch (Exception ex) {
            throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", ex.getMessage());
        }
    }
    @Override
    @Transactional
    public void blockCard(Long creditCardId) {
        try {
            var creditCard = creditCardRepository.findById(creditCardId)
                    .orElseThrow(() -> new NotFoundException("CARD_NOT_FOUND", "Cartão não encontrado"));


            if (creditCard.getStatus() == CreditCardStatus.BLOCKED) {
                throw new UnprocessableEntityException("CARD_ALREADY_BLOCKED", "O cartão já está bloqueado.");
            }
            creditCard.setStatus(CreditCardStatus.BLOCKED);
            creditCardRepository.save(creditCard);

        } catch (Exception ex) {
            throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", ex.getMessage());
        }
    }
    private static String generateNewCardNumber() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }

    @Override
    @Transactional
    public void processCvvUpdate(CvvUpdateWebhookRequest request, Long creditCardId) {
        try{
            if (request == null) {
                throw new IllegalArgumentException("O request de atualização de CVV não pode ser nulo.");
            }
            var creditCard = creditCardRepository.findById(creditCardId)
                    .orElseThrow(() -> new NotFoundException("CREDIT_CARD_NOT_FOUND",
                            "Cartão não encontrado para o ID: " + creditCardId));

            if (!creditCard.getType().equals(CreditCardType.VIRTUAL_CARD)) {
                throw new IllegalArgumentException("O CVV só pode ser atualizado para cartões virtuais.");
            }

            creditCard.setCvv(request.nextCvv());
            creditCard.setExpirationDate(request.expirationDate());


            creditCardRepository.save(creditCard);
        } catch (Exception ex) {
            throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", ex.getMessage());
        }
    }

    @Override
    @Transactional
    public void processDeliveryUpdate(CardDeliveryWebhookRequest request) {
        try{
            if (request == null) {
                throw new IllegalArgumentException("O request de atualização de entrega não pode ser nulo.");
            }

            var creditCard = creditCardRepository.findByTrackingId(request.trackingId())
                    .orElseThrow(() -> new NotFoundException("CREDIT_CARD_NOT_FOUND",
                            "Cartão não encontrado para o Tracking ID: " + request.trackingId()));

            creditCard.setDeliveryStatus(request.deliveryStatus());
            creditCard.setDeliveryDate(request.deliveryDate());
            creditCard.setDeliveryReturnReason(request.deliveryReturnReason());
            creditCard.setDeliveryAddress(request.deliveryAddress());

            creditCardRepository.save(creditCard);
        } catch (Exception ex) {
            throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", ex.getMessage());
        }
    }
}