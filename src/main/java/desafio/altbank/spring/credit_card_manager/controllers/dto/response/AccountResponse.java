package desafio.altbank.spring.credit_card_manager.controllers.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AccountResponse(
        Long id,
        String name,
        String email,
        String cpf,
        String phone,
        String address,
        LocalDateTime createdAt,
        Boolean status
) {

}
