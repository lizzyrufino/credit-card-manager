package desafio.altbank.spring.credit_card_manager.controllers;

import desafio.altbank.spring.credit_card_manager.controllers.dto.request.CardDeliveryWebhookRequest;
import desafio.altbank.spring.credit_card_manager.controllers.dto.request.CvvUpdateWebhookRequest;
import desafio.altbank.spring.credit_card_manager.services.CreditCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
    @RequiredArgsConstructor
    @RequestMapping("/webhooks")
    @Tag(name = "Webhooks")
    public class WebhookController {

        private final CreditCardService creditCardService;
    @Operation(description = "Recebe a resposta da entrega do cartao da transportadora")
    @GetMapping("/delivery-update")
    public ResponseEntity<Void> processDeliveryUpdate(@RequestParam CardDeliveryWebhookRequest request) {
        creditCardService.processDeliveryUpdate(request);
        return ResponseEntity.ok().build();
    }
    @Operation(description = "Recebe o webhook para atualização automática do CVV")
    @PostMapping("/cvv-update")
    public ResponseEntity<Void> cvvUpdateWebhook(@Valid @RequestBody CvvUpdateWebhookRequest request, Long creditCardId) {
        creditCardService.processCvvUpdate(request, creditCardId);
        return ResponseEntity.ok().build();
    }

}
