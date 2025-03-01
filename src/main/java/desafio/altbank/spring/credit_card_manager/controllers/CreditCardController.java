package desafio.altbank.spring.credit_card_manager.controllers;

import desafio.altbank.spring.credit_card_manager.controllers.dto.response.CreditCardResponse;
import desafio.altbank.spring.credit_card_manager.controllers.swagger.CreditCardControllerSwagger;
import desafio.altbank.spring.credit_card_manager.services.CreditCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/credit-card")
public class CreditCardController implements CreditCardControllerSwagger {
    private final CreditCardService creditCardService;
    @Override
    @PostMapping("/credit-card")
    public ResponseEntity<CreditCardResponse> createCreditCard(Long accountId) {
        creditCardService.createCard(accountId);
        return ResponseEntity.ok().build();
    }
    @Override
    @GetMapping("/credit-card")
    public ResponseEntity<List<CreditCardResponse>> listCards(Long accountId) {
        creditCardService.listCards(accountId);
        return ResponseEntity.ok().build();
    }
    @Override
    @PatchMapping("/{creditCardId}/reissue")
    public ResponseEntity<Void> reissueCard(Long creditCardId, String reason) {
        creditCardService.reissueCard(creditCardId,reason);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/{creditCardId}/activate")
    public ResponseEntity<Void> activateCard(Long creditCardId) {
        creditCardService.activateCard(creditCardId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/{creditCardId}/block")
    public ResponseEntity<Void> blockCreditCard(Long creditCardId) {
        creditCardService.blockCard(creditCardId);
        return ResponseEntity.noContent().build();
    }

}
