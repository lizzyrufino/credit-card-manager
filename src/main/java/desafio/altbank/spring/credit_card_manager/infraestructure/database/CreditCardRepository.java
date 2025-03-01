package desafio.altbank.spring.credit_card_manager.infraestructure.database;

import desafio.altbank.spring.credit_card_manager.domain.entities.Account;
import desafio.altbank.spring.credit_card_manager.domain.entities.CreditCard;
import desafio.altbank.spring.credit_card_manager.domain.enums.CreditCardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditCardRepository  extends JpaRepository<CreditCard, Long> {
    List<CreditCard> findByAccount(Account account);
    boolean existsByAccountAndStatus(Account account, CreditCardStatus status);
    Optional<CreditCard> findById(Long id);
    Optional<CreditCard> findByTrackingId(String trackingId);

}
