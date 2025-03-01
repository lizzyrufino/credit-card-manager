package desafio.altbank.spring.credit_card_manager.controllers.dto.request;


import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record CreateAccountRequest(
        @NotEmpty
        String name,
        @NotEmpty
        String email,
        @NotEmpty
        String cpf,
        @NotEmpty
        String phone,
        @NotEmpty
        String address

) {

}
