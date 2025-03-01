package desafio.altbank.spring.credit_card_manager.controllers.dto.request;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
@Builder
public record ReissueCreditCardRequest(
        @NotEmpty
        String reason
) {
}
