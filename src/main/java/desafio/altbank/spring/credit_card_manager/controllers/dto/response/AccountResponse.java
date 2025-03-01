package desafio.altbank.spring.credit_card_manager.controllers.dto.response;

import desafio.altbank.spring.credit_card_manager.domain.entities.Account;
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

    public static AccountResponse fromDomain(Account newAccount) {
        return AccountResponse.builder()
                .id(newAccount.getId())
                .name(newAccount.getName())
                .email(newAccount.getEmail())
                .cpf(newAccount.getCpf())
                .phone(newAccount.getPhone())
                .address(newAccount.getAddress())
                .createdAt(newAccount.getCreatedAt())
                .status(newAccount.getStatus())
                .build();
    }
}
