package desafio.altbank.spring.credit_card_manager.services;

import desafio.altbank.spring.credit_card_manager.configuration.MessageConfig;
import desafio.altbank.spring.credit_card_manager.controllers.dto.request.CreateAccountRequest;
import desafio.altbank.spring.credit_card_manager.controllers.dto.response.AccountResponse;
import desafio.altbank.spring.credit_card_manager.domain.entities.Account;
import desafio.altbank.spring.credit_card_manager.domain.ports.AccountPort;
import desafio.altbank.spring.credit_card_manager.exception.InternalServerErrorException;
import desafio.altbank.spring.credit_card_manager.exception.NotFoundException;
import desafio.altbank.spring.credit_card_manager.exception.UnprocessableEntityException;
import desafio.altbank.spring.credit_card_manager.infraestructure.database.AccountRepository;
import desafio.altbank.spring.credit_card_manager.infraestructure.database.CreditCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements AccountPort {
    private final AccountRepository accountRepository;
    private final CreditCardRepository creditCardRepository;
    private final MessageConfig messageConfig;

    @Override
    @Transactional
    public AccountResponse createAccount(CreateAccountRequest request) {
        try {
            if (accountRepository.existsByCpf(request.cpf())) {
                throw new UnprocessableEntityException("CPF_ALREADY_EXISTS",
                        String.format(messageConfig.getMessage("CPF_ALREADY_EXISTS", request.cpf())));
            }
            var newAccount = accountRepository.save(Account.builder()
                    .name(request.name())
                    .email(request.email())
                    .cpf(request.cpf())
                    .phone(request.phone())
                    .address(request.address())
                    .build());

            return AccountResponse.fromDomain(newAccount);

        } catch (Exception ex) {
            throw new InternalServerErrorException("UNEXPECTED_SERVER_ERROR", ex.getMessage());
        }
    }

    @Override
    public List<AccountResponse> listAccount(String cpf) {
        try {
            var account = (cpf == null) ? accountRepository.findAll() : accountRepository.findByCpf(cpf);
            return account.stream()
                    .map(AccountResponse::fromDomain)
                    .toList();

        } catch (Exception ex) {
            throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", ex.getMessage());
        }
    }
    @Override
    @Transactional
    public void inactivateAccount(Long accountId) {
        try {
            var account = accountRepository.findById(accountId)
                    .orElseThrow(() -> new NotFoundException("ACCOUNT_NOT_FOUND", "Conta não encontrada"));

            if (!account.getStatus()) {
                throw new UnprocessableEntityException("ACCOUNT_ALREADY_INACTIVE", "Conta já está inativa");
            }
            account.setStatus(false);
            accountRepository.save(account);

        } catch (Exception ex) {
            throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", ex.getMessage());
        }
    }

}

